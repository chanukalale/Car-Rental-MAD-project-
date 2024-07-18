package com.example.testingmadapp.CustomerHome;

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

import com.example.testingmadapp.CustomerHome.CustomerFragments.AvalableCarsFragment;
import com.example.testingmadapp.CustomerHome.CustomerFragments.BookingFragment;
import com.example.testingmadapp.CustomerHome.CustomerFragments.EventCusFragment;
import com.example.testingmadapp.EmployeeHome.EmpFragments.AddCarsFragment;
import com.example.testingmadapp.EmployeeHome.EmpFragments.AllcarsFragment;
import com.example.testingmadapp.EmployeeHome.EmpFragments.YourCarsFragment;
import com.example.testingmadapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerActivity extends AppCompatActivity {

    private BottomNavigationView bnav;
    private FrameLayout frm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer);

        bnav = findViewById(R.id.bnavcus);
        frm = findViewById(R.id.frmcus);

        loadFragment(new AvalableCarsFragment(), false);

        bnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.avlCars) {
                    loadFragment(new AvalableCarsFragment(), false);

                } else if (itemId == R.id.events) {
                    loadFragment(new EventCusFragment(), false);

                }
//                else if(itemId == R.id.signout){
//                    loadFragment(new AddCarsFragment(), false);
//
//                }
                else if (itemId == R.id.booking) {
                    loadFragment(new BookingFragment(), false);

                }

                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment, boolean isAppInsialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInsialized) {
            fragmentTransaction.add(R.id.frmcus, fragment);

        } else {

            fragmentTransaction.replace(R.id.frmcus, fragment);
        }

        fragmentTransaction.commit();
    }
}