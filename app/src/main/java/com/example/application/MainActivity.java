package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextUnit, editTextRebate;
    Spinner spinnerMonth;
    Button buttonCalculate, buttonView;
    TextView textViewTotalCharges, textViewFinalCost;
    ImageButton buttonAbout;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataHelper(this);

        editTextUnit = findViewById(R.id.editTextUnit);
        editTextRebate = findViewById(R.id.editTextRebate);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonView = findViewById(R.id.buttonView);
        buttonAbout = findViewById(R.id.buttonAbout);

        // Initialize the new TextViews
        textViewTotalCharges = findViewById(R.id.textViewTotalCharges);
        textViewFinalCost = findViewById(R.id.textViewFinalCost);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateAndStore();
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change this line:
                Intent intent = new Intent(MainActivity.this, BillListActivity.class);
                startActivity(intent);
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void calculateAndStore() {
        // Get input values
        String month = spinnerMonth.getSelectedItem().toString();
        String unitStr = editTextUnit.getText().toString();
        String rebateStr = editTextRebate.getText().toString();

        if (unitStr.isEmpty() || rebateStr.isEmpty()) {
            Toast.makeText(this, "Please enter unit and rebate", Toast.LENGTH_SHORT).show();
            return;
        }

        int units = Integer.parseInt(unitStr);
        double rebate = Double.parseDouble(rebateStr);

        // ✅ Add rebate validation here
        if (rebate < 0 || rebate > 5) {
            Toast.makeText(this, "Rebate must be between 0% and 5%", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalCharges = calculateCharges(units);
        double finalCost = totalCharges - (totalCharges * rebate / 100);

        // Display the results
        textViewTotalCharges.setText(String.format("Total Charges: RM %.2f", totalCharges));
        textViewFinalCost.setText(String.format("Final Cost: RM %.2f", finalCost));

        // Save to database
        ContentValues values = new ContentValues();
        values.put("month", month);
        values.put("unit", units);
        values.put("rebate", rebate);
        values.put("charges", totalCharges);
        values.put("final_cost", finalCost);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("bill", null, values);

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }


    private double calculateCharges(int unit) {
        double total = 0;

        if (unit <= 200) {
            total = unit * 0.218;
        } else if (unit <= 300) {
            total = (200 * 0.218) + ((unit - 200) * 0.334);
        } else if (unit <= 600) {
            total = (200 * 0.218) + (100 * 0.334) + ((unit - 300) * 0.516);
        } else {
            total = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((unit - 600) * 0.546);
        }

        return total;
    }
}
