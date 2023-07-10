package com.example.pharmacymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<Order> dataList;
    CorderAdapter adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.recyclerView6);

        bottomNavigationView = findViewById(R.id.bottomnavigation_01);
        bottomNavigationView.setSelectedItemId(R.id.order);

        bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.home:
                        // Handle item 1 selection
                        startActivity(new Intent(getApplicationContext(),CustomerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        // Handle item 2 selection
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.order:
                        return true;
                    case R.id.info:
                        // Handle item 3 selection
                        startActivity(new Intent(getApplicationContext(),DetailsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
        });

        dataList = new ArrayList<>();
        CorderAdapter adapter = new CorderAdapter(OrderHistoryActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this));

        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data list
                dataList.clear();

                // Get your phone number from shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String phoneNumber = sharedPreferences.getString("contactNumber", "");

                // Iterate through the dataSnapshot to get each order
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    // Get the order object
                    Order order = orderSnapshot.getValue(Order.class);

                    // Check if the order's phone number matches your phone number
                    if (order.getContactNumber().equals(phoneNumber)) {
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
}