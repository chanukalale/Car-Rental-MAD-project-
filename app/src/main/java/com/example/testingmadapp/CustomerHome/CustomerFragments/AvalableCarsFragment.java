package com.example.testingmadapp.CustomerHome.CustomerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testingmadapp.CustomerHome.DisplayCars.DisplayCarsAdapter;
import com.example.testingmadapp.CustomerHome.DisplayCars.DisplayCarsModel;
import com.example.testingmadapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvalableCarsFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    DisplayCarsAdapter myAdapter;
    ArrayList<DisplayCarsModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // item add ok
        View rootView =inflater.inflate(R.layout.fragment_avalable_cars, container, false);
        recyclerView = rootView.findViewById(R.id.rview3);
        database = FirebaseDatabase.getInstance().getReference().child("Cars");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        myAdapter = new DisplayCarsAdapter(getContext(), list);
        recyclerView.setAdapter(myAdapter);

        fetchDataFromDatabase();

        return rootView;
    }

    private void fetchDataFromDatabase() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String name = itemSnapshot.child("carName").getValue(String.class);
                    String primaryPayment = itemSnapshot.child("primaryPayment").getValue(String.class);
                    String image = itemSnapshot.child("carImage").getValue(String.class);
                    String amountOf1kmPrice = itemSnapshot.child("amountOf1kmPrice").getValue(String.class);
                    String seller = itemSnapshot.child("User").getValue(String.class);
                    String carID = itemSnapshot.getKey();

                    Log.d("AllcarsFragment", "Name: " + name);
                    Log.d("AllcarsFragment", "Primary Payment: " + primaryPayment);
                    Log.d("AllcarsFragment", "Image: " + image);
                    Log.d("AllcarsFragment", "Amount per KM: " + amountOf1kmPrice);
                    Log.d("AllcarsFragment", "Seller: " + seller);

                    DisplayCarsModel mainModel = new DisplayCarsModel();
                    mainModel.setName(name);
                    mainModel.setOneKMPrice(amountOf1kmPrice);
                    mainModel.setPrimaryPayment(primaryPayment);
                    mainModel.setImage(image);
                    mainModel.setSeller(seller);
                    mainModel.setCarID(carID);

                    list.add(mainModel);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("AllcarsFragment", "Database error: " + error.getMessage());
            }
        });
    }
}