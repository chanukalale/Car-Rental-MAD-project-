package com.example.testingmadapp.CustomerHome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testingmadapp.CustomerHome.DisplayCars.DisplayCarsModel;
import com.example.testingmadapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RentActivity extends AppCompatActivity {

    TextView selectedCar, numPlate, pp, aop, sDate, eDate, sDateDone, eDateDone;
    Button rent;
    CalendarView sCal, eCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rent);

        selectedCar = findViewById(R.id.selectedCar);
        numPlate = findViewById(R.id.numPlate);
        pp = findViewById(R.id.pp);
        aop = findViewById(R.id.aop);
        sDate = findViewById(R.id.sDate);
        eDate = findViewById(R.id.eDate);
        sDateDone = findViewById(R.id.sDateDone);
        eDateDone = findViewById(R.id.eDateDone);

        sCal = findViewById(R.id.sCal);
        eCal = findViewById(R.id.eCal);

        rent = findViewById(R.id.rent);

        // Get itemCode from intent
        Intent intent = getIntent();
        String itemCode = intent.getStringExtra("ItemCode");

        //database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Cars").child(itemCode);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String name = snapshot.child("carName").getValue(String.class);
                    String primaryPayment = snapshot.child("primaryPayment").getValue(String.class);
                    String num = snapshot.child("numPlate").getValue(String.class);
                    String amountOf1kmPrice = snapshot.child("amountOf1kmPrice").getValue(String.class);

                    selectedCar.setText(name);
                    numPlate.setText(num);
                    pp.setText(primaryPayment);
                    aop.setText(amountOf1kmPrice);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        //select start date
        sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sCal.setVisibility(View.VISIBLE);
                sDateDone.setVisibility(View.VISIBLE);
            }
        });

        //done button on select start date
        sDateDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sCal.setVisibility(View.GONE);
                sDateDone.setVisibility(View.GONE);
            }
        });

        // Set a date change listener
        sCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                sDate.setText(selectedDate);
            }
        });


        //select end date
        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eCal.setVisibility(View.VISIBLE);
                eDateDone.setVisibility(View.VISIBLE);
            }
        });

        //done button on select start date
        eDateDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eCal.setVisibility(View.GONE);
                eDateDone.setVisibility(View.GONE);
            }
        });

        // Set a date change listener
        eCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                eDate.setText(selectedDate);
            }
        });
    }
}