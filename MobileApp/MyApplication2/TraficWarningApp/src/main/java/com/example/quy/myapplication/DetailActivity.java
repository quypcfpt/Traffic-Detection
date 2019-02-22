package com.example.quy.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView streetName = findViewById(R.id.roadName);
        streetName.setText(getIntent().getStringExtra("NAME_ROAD"));
    }
}
