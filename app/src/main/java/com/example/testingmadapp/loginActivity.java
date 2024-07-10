package com.example.testingmadapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testingmadapp.CustomerHome.CustomerActivity;
import com.example.testingmadapp.EmployeeHome.EmployeeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    DatabaseReference database;
    EditText loginName, loginPass;
    Button buttonlogin;
    TextView toreg;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //sharedPreferences
        sharedPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //edit text
        loginName = findViewById(R.id.loginName);
        loginPass = findViewById(R.id.loginPass);

        //button
        buttonlogin = findViewById(R.id.buttonlogin);

        //to register
        toreg = findViewById(R.id.toreg);

        database = FirebaseDatabase.getInstance().getReference().child("Users");

        //to register
        toreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        //login button
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = loginName.getText().toString();
                String pass = loginPass.getText().toString();

                if(name.isEmpty() || pass.isEmpty()){
                    Toast.makeText(loginActivity.this,"Fill all fields",Toast.LENGTH_SHORT).show();
                }else{
                    database.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                                    String userId = userSnapshot.getKey();
                                    String getPass = userSnapshot.child("password").getValue(String.class);
                                    String gettype = userSnapshot.child("type").getValue(String.class);

                                    if (pass != null && pass.equals(getPass)) {

                                        editor.putString("userId", userId);
                                        editor.putString("type", gettype);
                                        editor.putBoolean("logged", true);
                                        editor.apply();

                                        if ("customer".equals(gettype)) {
                                            Toast.makeText(loginActivity.this, "Successfully logged into Customer account", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(loginActivity.this, CustomerActivity.class));
                                            finish();

                                        } else if ("admin".equals(gettype)) {
                                            Toast.makeText(loginActivity.this, "Successfully logged into Admin account", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(loginActivity.this, EmployeeActivity.class));
                                            finish();

                                        } else {
                                            Toast.makeText(loginActivity.this, "Unknown user type", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                Toast.makeText(loginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(loginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(loginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}