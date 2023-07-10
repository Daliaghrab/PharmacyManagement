package com.example.pharmacymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton emp;
    ImageButton customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Toast.makeText(getApplicationContext(), "Toast 1", Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emp = findViewById(R.id.imageButton);
        customer = findViewById(R.id.imageButton2);


        emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start EmployeeActivity
                Intent intent = new Intent(getApplicationContext(), EmployeeActivity.class);
                startActivity(intent);
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CustomerActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}