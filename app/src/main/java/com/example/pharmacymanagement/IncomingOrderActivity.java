package com.example.pharmacymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IncomingOrderActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<Order> dataList;
    CorderAdapter adapter;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_order);

        recyclerView = findViewById(R.id.recyclerView7);

        bottomNavigationView = findViewById(R.id.bottomnavigation_02);
        bottomNavigationView.setSelectedItemId(R.id.eorder);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.emphome:
                    startActivity(new Intent(getApplicationContext(),EmployeeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.eorder:
                    return true;
                case R.id.stock:
                    startActivity(new Intent(getApplicationContext(),productaddActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;

        });

        dataList = new ArrayList<>();
        CorderAdapter adapter = new CorderAdapter(IncomingOrderActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(IncomingOrderActivity.this));

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

                    // Add the order to the data list
                    dataList.add(order);
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
}