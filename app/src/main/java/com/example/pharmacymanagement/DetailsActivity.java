package com.example.pharmacymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    EditText email;
    EditText addr;
    EditText city;
    EditText province;
    EditText contactNumber;

    ImageButton save;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        bottomNavigationView = findViewById(R.id.bottomnavigation_01);
        bottomNavigationView.setSelectedItemId(R.id.info);
        save = findViewById(R.id.imageButton8);

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
                        // Handle item 2 selection
                        startActivity(new Intent(getApplicationContext(),OrderHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.info:
                        return true;
                }
                return false;
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = findViewById(R.id.editTextTextPersonName2);
                EditText addrEditText = findViewById(R.id.editTextTextPersonName3);
                EditText cityEditText = findViewById(R.id.editTextTextPersonName4);
                EditText provinceEditText = findViewById(R.id.editTextTextPersonName6);
                EditText contactNumberEditText = findViewById(R.id.editTextTextPersonName5);

                // Get the values from the EditText fields
                String email = emailEditText.getText().toString();
                String addr = addrEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String province = provinceEditText.getText().toString();
                String contactNumber = contactNumberEditText.getText().toString();

                // Check if any of the EditText fields are empty
                if (email.isEmpty() || addr.isEmpty() || city.isEmpty() || province.isEmpty() || contactNumber.isEmpty()) {
                    Toast.makeText(DetailsActivity.this, "Fields Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadData();
                }
            }
        });
    }
    public void uploadData() {
        EditText emailEditText = findViewById(R.id.editTextTextPersonName2);
        EditText addrEditText = findViewById(R.id.editTextTextPersonName3);
        EditText cityEditText = findViewById(R.id.editTextTextPersonName4);
        EditText provinceEditText = findViewById(R.id.editTextTextPersonName6);
        EditText contactNumberEditText = findViewById(R.id.editTextTextPersonName5);

        String email = emailEditText.getText().toString();
        String addr = addrEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String province = provinceEditText.getText().toString();
        String contactNumber = contactNumberEditText.getText().toString();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        Query contactNumberQuery = usersRef.orderByChild("contactnumber").equalTo(contactNumber).limitToFirst(1);

        contactNumberQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Contact number already exists in the database
                    Toast.makeText(DetailsActivity.this, "Contact number already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the user data
                    String userId = usersRef.push().getKey();
                    User user = new User(userId, email, addr, city, province, contactNumber);
                    usersRef.child(userId).setValue(user);
                    Toast.makeText(DetailsActivity.this, "User data saved", Toast.LENGTH_SHORT).show();
                    saveShippingDetails22(email, addr, city, province, contactNumber);
                    saveShippingDetails();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
    public boolean hasStoredShippingDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("hasStoredShippingDetails", false);
    }
    private void saveShippingDetails() {
        // Save the shipping details

        // Update shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("hasStoredShippingDetails", true);
        editor.apply();
    }
    private void saveShippingDetails22(String email, String addr, String city, String province, String contactNumber) {
        // Save the shipping details

        // Update shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("addr", addr);
        editor.putString("city", city);
        editor.putString("province", province);
        editor.putString("contactNumber", contactNumber);
        editor.apply();
    }

}