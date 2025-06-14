package com.example.application;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewDetail = findViewById(R.id.textViewDetail);

        // Receive intent data
        String month = getIntent().getStringExtra("month");
        int unit = getIntent().getIntExtra("unit", 0);
        double rebate = getIntent().getDoubleExtra("rebate", 0);
        double charges = getIntent().getDoubleExtra("charges", 0);
        double finalCost = getIntent().getDoubleExtra("final", 0);

        String detail = "Month: " + month +
                "\nUnits: " + unit +
                "\nRebate: " + rebate + "%" +
                "\nTotal Charges: RM " + charges +
                "\nFinal Cost: RM " + finalCost;

        textViewDetail.setText(detail);
    }
}
