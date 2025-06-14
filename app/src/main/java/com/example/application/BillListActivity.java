package com.example.application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BillListActivity extends AppCompatActivity {

    String[] records;
    ListView listView;
    DataHelper dbHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);

        listView = findViewById(R.id.listViewData);
        dbHelper = new DataHelper(this);

        refreshList();
    }

    public void refreshList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM bill", null);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No saved bills found", Toast.LENGTH_SHORT).show();
            return;
        }

        records = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            records[i] = cursor.getString(1) + " - RM " + cursor.getDouble(5);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, records);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            cursor.moveToPosition(position);
            Intent intent = new Intent(BillListActivity.this, DetailActivity.class);
            intent.putExtra("month", cursor.getString(1));
            intent.putExtra("unit", cursor.getInt(2));
            intent.putExtra("rebate", cursor.getDouble(3));
            intent.putExtra("charges", cursor.getDouble(4));
            intent.putExtra("final", cursor.getDouble(5));
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
    }
}



