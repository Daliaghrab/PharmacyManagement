package com.example.pharmacymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<Order> dataList;
    EorderAdapter adapter;

    BottomNavigationView bottomNavigationView;

    ImageButton addButton;

    TextView revenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        recyclerView = findViewById(R.id.recyclerView9);

        bottomNavigationView = findViewById(R.id.bottomnavigation_02);
        bottomNavigationView.setSelectedItemId(R.id.emphome);
        addButton = findViewById(R.id.imageButton9);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.emphome:
                    return true;
                case R.id.eorder:
                    startActivity(new Intent(getApplicationContext(),IncomingOrderActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.stock:
                    startActivity(new Intent(getApplicationContext(),productaddActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;

        });


        setTotalRevenueTextView();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Addproduct2Activity
                Intent intent = new Intent(getApplicationContext(), Addproduct2Activity.class);
                startActivity(intent);
            }
        });



        dataList = new ArrayList<>();
        EorderAdapter adapter = new EorderAdapter(EmployeeActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(EmployeeActivity.this));

        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data list
                dataList.clear();

                // Iterate through the dataSnapshot to get each order
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    // Get the order object
                    Order order = orderSnapshot.getValue(Order.class);

                    // Check if the order status is not "Completed"
                    if (!"completed".equals(order.getOrderstatus())) {
                        // Add the order to the data list
                        dataList.add(order);
                    }
                }

                // Update the adapter with the new data
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });


    }
    private void setTotalRevenueTextView() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double totalRevenue = 0.0;

                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    Order order = orderSnapshot.getValue(Order.class);

                    if (order.getOrderstatus().equals("completed")) {
                        String orderTotalPrice = order.getTotprice().replace("$", "").trim();
                        double orderTotal = Double.parseDouble(orderTotalPrice);
                        totalRevenue += orderTotal;
                    }
                }

                TextView revenueTextView = findViewById(R.id.textView60);
                revenueTextView.setText(String.format("$ %.2f", totalRevenue));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }
}