package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity{
    String filename="";
    private Button button,button2,button3,button4,button5;
    private EditText filename_gen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        filename_gen= findViewById(R.id.filename_gen);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button5=findViewById(R.id.button5);
        button4=findViewById(R.id.button4);

        AudioManager am= (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        button.setOnClickListener(v -> switchToTranscribe());
        button2.setOnClickListener(v -> switchToInk());
        button3.setOnClickListener(v -> filegen());
        button4.setOnClickListener(v-> switchToEditorMode());
        button5.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "Dictator");
            intent.setDataAndType(uri, "*/*");
            startActivity(intent);
        });
    }

    private void switchToTranscribe() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss", Locale.getDefault());
        String formattedDate = df.format(c);
        Intent switchActivityIntent = new Intent(this, TranscriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("stuff", filename);
        switchActivityIntent.putExtras(bundle);
        startActivity(switchActivityIntent);
    }

    private void switchToInk() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss", Locale.getDefault());
        String formattedDate = df.format(c);
        Intent switchActivityIntent1 = new Intent(this, DigitalInkMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("stuff", filename);
        filename=formattedDate;
        switchActivityIntent1.putExtras(bundle);
        startActivity(switchActivityIntent1);
    }

    public void switchToEditorMode() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss", Locale.getDefault());
        String formattedDate = df.format(c);
        Intent switchActivityIntent1 = new Intent(this, EditorModeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("stuff", filename);
        filename=formattedDate;
        switchActivityIntent1.putExtras(bundle);
        startActivity(switchActivityIntent1);
    }

    private void filegen(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss", Locale.getDefault());
        String formattedDate = df.format(c);
        filename_gen.setText(formattedDate);
        filename=formattedDate;

    }


}