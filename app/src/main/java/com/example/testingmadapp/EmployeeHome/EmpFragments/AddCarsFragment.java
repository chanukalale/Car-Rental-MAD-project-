package com.example.testingmadapp.EmployeeHome.EmpFragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testingmadapp.EmployeeHome.EmployeeActivity;
import com.example.testingmadapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddCarsFragment extends Fragment {

    EditText carname, numplate, primarypay, distance;
    Button btn;
    ImageView img;
    String user, key;
    DatabaseReference DB;
    StorageReference storageReference;
    Uri uri;
    int PICK_IMAGE_REQUEST;


    String name, numplt, prpay, dist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_cars, container, false);

        carname = rootView.findViewById(R.id.carname);
        numplate = rootView.findViewById(R.id.numplate);
        primarypay = rootView.findViewById(R.id.primarypay);
        distance = rootView.findViewById(R.id.distance);
        img = rootView.findViewById(R.id.carimg);

        btn = rootView.findViewById(R.id.addbutton);

        SharedPreferences sharedPreference = requireActivity().getSharedPreferences("CurrentUser", getContext().MODE_PRIVATE);
        user = sharedPreference.getString("userName","");

        if (user == null || user.isEmpty()) {

            Toast.makeText(getContext(), "User name is missing", Toast.LENGTH_SHORT).show();
        }

        storageReference = FirebaseStorage.getInstance().getReference();

        DB = FirebaseDatabase.getInstance().getReference().child("Cars");
        key = DB.child("Cars").push().getKey();

        PICK_IMAGE_REQUEST = 2;

        //image open
        img.setOnClickListener(new View.OnClickListener() {
            @Nullable
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "img clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = carname.getText().toString();
                numplt = numplate.getText().toString();
                prpay = primarypay.getText().toString();
                dist = distance.getText().toString();

                if(name.isEmpty() || numplt.isEmpty() || prpay.isEmpty() || dist.isEmpty()){

                    Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                }else{

                    if (uri != null) {
                        StorageReference file = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
                        file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String imageUrl = uri.toString();
                                        DB.child(key).child("carImage").setValue(imageUrl);
                                        DB.child(key).child("carName").setValue(name);
                                        DB.child(key).child("numPlate").setValue(numplt);
                                        DB.child(key).child("amountOf1kmPrice").setValue(prpay);
                                        DB.child(key).child("primaryPayment").setValue(dist);
                                        DB.child(key).child("User").setValue(user);

                                        Intent i = new Intent(getActivity(), EmployeeActivity.class);
                                        startActivity(i);

                                        Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rootView;
    }

    private String getFileExtension(Uri uri) {

        if (uri == null) {
            return null;
        }
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();

            if (uri != null) {
                img.setImageURI(uri);

            } else {
                Toast.makeText(getContext(), "Error: Image not selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}