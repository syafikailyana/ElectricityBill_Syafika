package com.example.application;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView textViewLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textViewLink = findViewById(R.id.textViewUrl);

        // Set click listener to open GitHub or any link
        textViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change this to your GitHub later
                String url = "https://github.com/yourname/yourproject";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
