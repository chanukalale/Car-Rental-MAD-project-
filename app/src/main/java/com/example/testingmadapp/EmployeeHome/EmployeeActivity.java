package com.example.testingmadapp.EmployeeHome;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        loadFragment(new YourCarsFragment(), false);

        bnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if(itemId == R.id.yourCars){
                    loadFragment(new YourCarsFragment(), false);

                }else if(itemId == R.id.addCars){
                    loadFragment(new AddCarsFragment(), false);

                }
//                else if(itemId == R.id.signout){
//                    loadFragment(new AddCarsFragment(), false);
//
//                }
                else if(itemId == R.id.allCars){
                    loadFragment(new AllcarsFragment(), false);

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
}