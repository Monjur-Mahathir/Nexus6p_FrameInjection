package com.example.frame_injection_6p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Button button1;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.b1);
        textView1 = (TextView)findViewById(R.id.tv1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inject();
                textView1.setText("Status: Done");
            }
        });


    }

    public void inject() {
        try {
            // Executes the command.
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream stdin = new DataOutputStream(p.getOutputStream());
            // from here all commands are executed with su permissions
            stdin.writeBytes("nexutil -k149/20\n");
            stdin.writeBytes("LD_PRELOAD=libnexmon.so aireplay-ng -9 wlan0\n"); // \n executes the command
            stdin.flush();
            stdin.close();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}