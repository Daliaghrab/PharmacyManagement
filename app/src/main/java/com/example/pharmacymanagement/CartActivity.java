package com.example.pharmacymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<ProductClass> dataList;
    cartAdapter adapter;
    AlertDialog dialog;
    BottomNavigationView bottomNavigationView;

    ImageView boolBG;
    ImageView boolICO;
    TextView boolTXT;

    ImageButton placeorder;
    TextView bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerView3);
        bill =  findViewById(R.id.textView25);
        bottomNavigationView = findViewById(R.id.bottomnavigation_01);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        placeorder = findViewById(R.id.imageButton7);
        boolBG = findViewById(R.id.imageView24);
        boolICO = findViewById(R.id.imageView26);
        boolTXT = findViewById(R.id.textView56);

        bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.home:
                        // Handle item 1 selection
                        startActivity(new Intent(getApplicationContext(),CustomerActivity.class));

                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        return true;
                    case R.id.order:
                        // Handle item 2 selection
                        startActivity(new Intent(getApplicationContext(),OrderHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.info:
                        // Handle item 3 selection
                        startActivity(new Intent(getApplicationContext(),DetailsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
        });

        adapter = new cartAdapter(CartActivity.this, productAdapter2.cartItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        double totalBill = 0;
        for (ProductClass item : productAdapter2.cartItems) {
            double price = Double.parseDouble(item.getProdPrice());
            totalBill += price;
        }

        TextView bill = findViewById(R.id.textView25);
        bill.setText(String.format("Total: $%.2f", totalBill));

        SharedPreferences sharedPreferencess = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferencess.getBoolean("hasStoredShippingDetails", false)){
            //true
            boolTXT.setVisibility(View.GONE);
            boolICO.setVisibility(View.GONE);
            boolBG.setVisibility(View.GONE);
        }
        else{
            //didnt stored shipping info
            Toast.makeText(CartActivity.this, "Shipping info not stored", Toast.LENGTH_SHORT).show();
            boolTXT.setVisibility(View.VISIBLE);
            boolICO.setVisibility(View.VISIBLE);
            boolBG.setVisibility(View.VISIBLE);
        }

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                if (sharedPreferences.getBoolean("hasStoredShippingDetails", false)) {
                    // Shipping info is stored

                    // Get the stored shipping details from shared preferences
                    String email = sharedPreferences.getString("email", "");
                    String addr = sharedPreferences.getString("addr", "");
                    String city = sharedPreferences.getString("city", "");
                    String province = sharedPreferences.getString("province", "");
                    String contactNumber = sharedPreferences.getString("contactNumber", "");

                    // Create a new User object
                    User user = new User(email, addr, city, province, contactNumber);

                    // Create a new Order object with the User parameter
                    Order order = new Order(email, addr, city, province, contactNumber, productAdapter2.cartItems, user);

                    // Save the order to the Firebase database
                    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
                    String orderId = ordersRef.push().getKey();
                    order.setOrderId(orderId); // Set the generated order ID
                    ordersRef.child(orderId).setValue(order);

                    Toast.makeText(CartActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
                    adapter.clearCartItems();
                    productAdapter2.cartItems.clear();

                } else {
                    // Shipping info is not stored
                    Toast.makeText(CartActivity.this, "Shipping info not stored", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void updateTotalBill(double totalBill) {
        TextView billTextView = findViewById(R.id.textView25);
        bill.setText(String.format("Total: $%.2f", totalBill));
    }
}