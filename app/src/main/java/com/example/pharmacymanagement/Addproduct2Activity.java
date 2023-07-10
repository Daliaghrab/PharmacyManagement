package com.example.pharmacymanagement;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Addproduct2Activity extends AppCompatActivity {

    ImageView uploadImage;
    ImageButton saveButton;
    EditText uploadName, uploadDesc,  uploadPrice, uploadQuantity;
    AutoCompleteTextView autoCompleteTextView;

    String imageURL;
    Uri uri;


    String chosenval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct2);

        uploadImage = findViewById(R.id.imageView18);
        saveButton = findViewById(R.id.imageButton10);
        uploadName = findViewById(R.id.editTextTextPersonName7);
        uploadDesc = findViewById(R.id.editTextTextPersonName11);
        uploadPrice = findViewById(R.id.editTextTextPersonName8);
        uploadQuantity = findViewById(R.id.editTextTextPersonName12);




        chosenval = null;

        autoCompleteTextView = findViewById(R.id.AutoCompleteTextview);
        String[] options=new String[]{"N/A"};
        options = new String[]{"Medicene","First Aid","Syrups","Baby care","Supplements","Herbal"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, options);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosenval = autoCompleteTextView.getText().toString();
                Toast.makeText(getApplicationContext(), chosenval, Toast.LENGTH_SHORT).show();
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(Addproduct2Activity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = uploadName.getText().toString().trim();
                String desc = uploadDesc.getText().toString().trim();
                String price = uploadPrice.getText().toString().trim();
                String quantity = uploadQuantity.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(price) || TextUtils.isEmpty(quantity)){
                    Toast.makeText(Addproduct2Activity.this, "Data missing", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveData();
                }
            }
        });
    }


    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(Addproduct2Activity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }


    public void uploadData(){
        String title = uploadName.getText().toString();
        String desc = uploadDesc.getText().toString();
        String category_chosen = chosenval;
        String price = uploadPrice.getText().toString();
        String Quantity = uploadQuantity.getText().toString();
        ProductClass dataClass = new ProductClass(title, imageURL, desc, category_chosen, price, Quantity);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.


        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Pharmacy").child(currentDate)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Addproduct2Activity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Addproduct2Activity.this, "Error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}