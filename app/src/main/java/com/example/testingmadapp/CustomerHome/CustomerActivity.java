package com.example.testingmadapp.CustomerHome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testingmadapp.CustomerHome.CustomerFragments.AvalableCarsFragment;
import com.example.testingmadapp.CustomerHome.CustomerFragments.BookingFragment;
import com.example.testingmadapp.R;
import com.example.testingmadapp.loginActivity;
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

                }
                else if(itemId == R.id.signoutcus){
                    signOutCus();

                }
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

    public void signOutCus(){

        //Alert of click cansel when status pending

        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
        builder.setMessage("Do you want to logout ?");

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent i = new Intent(CustomerActivity.this, loginActivity.class);
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