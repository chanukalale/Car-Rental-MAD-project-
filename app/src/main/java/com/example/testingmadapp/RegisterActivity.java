package com.example.testingmadapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference  database;
    EditText rfullname, ruser, remail, rphone, rlic, rpass;
    TextView golog;
    CheckBox cus, employee;
    String type;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize database reference
        database = FirebaseDatabase.getInstance().getReference().child("Users");


        // Initialize UI elements
        rfullname = findViewById(R.id.rfullname);
        ruser = findViewById(R.id.ruser);
        remail = findViewById(R.id.remail);
        rphone = findViewById(R.id.rphone);
        rlic = findViewById(R.id.rlic);
        rpass = findViewById(R.id.rpass);
        login = findViewById(R.id.logbutton);
        cus = findViewById(R.id.cuschk);
        employee = findViewById(R.id.empchk);

        //text view
        golog = findViewById(R.id.golog);

        golog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, loginActivity.class);
                startActivity(i);
            }
        });

        // Set onClick listener for the login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Determine user type
                if ((cus.isChecked() && employee.isChecked()) || (!cus.isChecked() && !employee.isChecked())) {
                    type = "invalid";
                } else if (cus.isChecked()) {
                    type = "customer";
                } else if (employee.isChecked()) {
                    type = "employee";
                }

                System.out.println(type);
                // Get user input
                String name = rfullname.getText().toString();
                String uName = ruser.getText().toString().trim();
                String email = remail.getText().toString();
                String phone = rphone.getText().toString();
                String lic = rlic.getText().toString();
                String pass = rpass.getText().toString();

                // Validate user input and type
                if (type.equals("invalid")) {
                    Toast.makeText(RegisterActivity.this, "Invalid user type", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty() || uName.isEmpty() || email.isEmpty() || phone.isEmpty() || lic.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {

                    System.out.println("done");
                    // Check if username exists in the database
                    database.orderByChild("userName").equalTo(uName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "User Name already exists!", Toast.LENGTH_SHORT).show();
                            } else {

                                String key = database.push().getKey();
                                if (key != null) {

                                    database.child(key).child("UserName").setValue(uName);
                                    database.child(key).child("Name").setValue(name);
                                    database.child(key).child("Email").setValue(email);
                                    database.child(key).child("Phone").setValue(phone);
                                    database.child(key).child("License").setValue(lic);
                                    database.child(key).child("Password").setValue(pass);
                                    database.child(key).child("UserType").setValue(type);

                                    Toast.makeText(RegisterActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Error generating key", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(RegisterActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    System.out.println("done2");

                }
            }
        });
    }
}