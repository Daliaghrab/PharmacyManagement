package com.example.pharmacymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<ProductClass> dataList;
    productAdapter2 adapter;
    ImageButton medicene;
    ImageButton firstaid;
    ImageButton herbel;
    ImageButton supplements;
    ImageButton babycare;
    ImageButton syrup;
    AlertDialog dialog;
    private String selectedFilterCategory = "";

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        recyclerView = findViewById(R.id.recyclerView2);
        medicene = findViewById(R.id.imageButton3);
        firstaid = findViewById(R.id.imageButton4);
        herbel = findViewById(R.id.imageButton5);
        supplements = findViewById(R.id.imageButton6);
        babycare = findViewById(R.id.imageView11);
        syrup = findViewById(R.id.imageButton11);



        bottomNavigationView = findViewById(R.id.bottomnavigation_01);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    return true;
                case R.id.cart:
                    startActivity(new Intent(getApplicationContext(),CartActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.order:
                    startActivity(new Intent(getApplicationContext(),OrderHistoryActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.info:
                    startActivity(new Intent(getApplicationContext(),DetailsActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;

        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(CustomerActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new productAdapter2(CustomerActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Pharmacy");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    ProductClass dataClass = itemSnapshot.getValue(ProductClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        medicene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFilterCategory = "Medicene";
                applyFilter();
            }
        });

        firstaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFilterCategory = "First Aid";
                applyFilter();
            }
        });
        herbel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFilterCategory = "Herbal";
                applyFilter();
            }
        });
        supplements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFilterCategory = "Supplements";
                applyFilter();
            }
        });
        syrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFilterCategory = "Syrups";
                applyFilter();
            }
        });
        babycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFilterCategory = "Baby care";
                applyFilter();
            }
        });
    }
    private void applyFilter() {
        List<ProductClass> filteredList = new ArrayList<>();

        for (ProductClass product : dataList) {
            if (product.getProdCategory().equalsIgnoreCase(selectedFilterCategory)) {
                filteredList.add(product);
            }
        }

        dialog.show();

        new CountDownTimer(500, 500) {
            public void onTick(long millisUntilFinished) {
                // Do nothing on each tick
            }
            public void onFinish() {
                dialog.dismiss();
                adapter.setDataList(filteredList);
                adapter.notifyDataSetChanged();
            }
        }.start();






    }

}