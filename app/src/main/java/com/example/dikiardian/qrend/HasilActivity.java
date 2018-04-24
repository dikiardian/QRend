package com.example.dikiardian.qrend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HasilActivity extends AppCompatActivity {

    int idEvent;
    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        idEvent = intent.getIntExtra("eventId",0);
        eventName = intent.getStringExtra("eventName");

        setTitle(eventName);
    }
}
