package com.example.demo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class EditorModeActivity extends AppCompatActivity {

    private EditText txvResult;
    FileOutputStream fOut=null;
    private Button save,clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        txvResult = (EditText) findViewById(R.id.txvResult);
        save=findViewById(R.id.button7);
        clear=findViewById(R.id.button8);
        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            if(Build.VERSION.SDK_INT>=23){
                if(checkPermission()){// Folder Name
                    File directory = new File(Environment.getExternalStorageDirectory() + java.io.File.separator +"Dictator");
                    if (!directory.exists())
                        System.out.println(directory.mkdirs() ? "Directory has been created" : "Directory not created");
                    else
                        System.out.println("Directory exists");
                    Bundle bundle = getIntent().getExtras();
                    String filename = bundle.getString("stuff");
                    File myFile = new File(directory, filename+"-Editor.txt");
                    try {
                        fOut=new FileOutputStream(myFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    requestPermission();
                }
            }else{
                File directory = new File(Environment.getExternalStorageDirectory() + java.io.File.separator +"Dictator");
                if (!directory.exists())
                    System.out.println(directory.mkdirs() ? "Directory has been created" : "Directory not created");
                else
                    System.out.println("Directory exists");
                Bundle bundle = getIntent().getExtras();
                String filename = bundle.getString("stuff");
                File myFile = new File(directory, filename+"-Ink.txt");
                try {
                    fOut=new FileOutputStream(myFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        clear.setOnClickListener(v->{
            txvResult.setText("");
        });

        save.setOnClickListener(v->{
            try {
                fOut.write(txvResult.getText().toString().getBytes());
                txvResult.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(EditorModeActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(EditorModeActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(EditorModeActivity.this, "Write External Storage permission allows us to create files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(EditorModeActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.append(result.get(0)+" ");
                }
                break;
        }
    }
}