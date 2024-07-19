package com.example.testingmadapp.EmployeeHome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testingmadapp.EmployeeHome.EmpFragments.AddCarsFragment;
import com.example.testingmadapp.EmployeeHome.EmpFragments.AllcarsFragment;
import com.example.testingmadapp.EmployeeHome.EmpFragments.YourCarsFragment;
import com.example.testingmadapp.R;
import com.example.testingmadapp.loginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeActivity extends AppCompatActivity {

    private BottomNavigationView bnav;
    private FrameLayout frm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee);

        bnav = findViewById(R.id.bnav);
        frm = findViewById(R.id.frm);

        loadFragment(new AllcarsFragment(), false);

        bnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if(itemId == R.id.allCars){
                    loadFragment(new AllcarsFragment(), false);

                }else if(itemId == R.id.yourCars){
                    loadFragment(new YourCarsFragment (), false);

                }
                else if(itemId == R.id.signout){
                    signOut();

                }
                else if(itemId == R.id.addCars){
                    loadFragment(new AddCarsFragment(), false);

                }

                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment, boolean isAppInsialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInsialized){
            fragmentTransaction.add(R.id.frm, fragment);

        }else{

            fragmentTransaction.replace(R.id.frm, fragment);
        }

        fragmentTransaction.commit();
    }

    public void signOut(){

        //Alert of click cansel when status pending

        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeActivity.this);
        builder.setMessage("Do you want to logout ?");

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent i = new Intent(EmployeeActivity.this, loginActivity.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}