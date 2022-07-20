package com.example.hospitaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class tracker extends AppCompatActivity {
    TextView linktext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        linktext=findViewById(R.id.link);
        linktext.setMovementMethod(LinkMovementMethod.getInstance());
    }
}