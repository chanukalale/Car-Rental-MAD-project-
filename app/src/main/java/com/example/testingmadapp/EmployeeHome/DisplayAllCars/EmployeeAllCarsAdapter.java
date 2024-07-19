package com.example.testingmadapp.EmployeeHome.DisplayAllCars;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testingmadapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EmployeeAllCarsAdapter extends RecyclerView.Adapter<EmployeeAllCarsAdapter.MainViewHolder>{

    Context context;
    ArrayList<EmployeeAllCarsModel> list;

    public EmployeeAllCarsAdapter(Context context, ArrayList<EmployeeAllCarsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rent_cars_card, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        EmployeeAllCarsModel model = list.get(position);

        // Load image
        if (model.getImage() != null && !model.getImage().isEmpty()) {
            new LoadImageTask(holder.itemImage).execute(model.getImage());
        } else {
            holder.itemImage.setImageResource(R.drawable.ic_launcher_background);
        }

                SharedPreferences sharedPreferences = context.getSharedPreferences("CurrentUser", context.MODE_PRIVATE);
                String customer = sharedPreferences.getString("userEmail", "");

        // Set item name
        holder.itemName.setText(model.getName());

        // Set item price
        holder.amountOneKM.setText(model.getOneKMPrice());

        //Set Available qty
        holder.primaryPay.setText(model.getPrimaryPayment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, amountOneKM, primaryPay;
        LinearLayout moreinfoOfAvailableCars;
        ImageView itemImage;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            moreinfoOfAvailableCars = itemView.findViewById(R.id.moreinfoOfAvailableCars);
            itemName = itemView.findViewById(R.id.chforName);
            amountOneKM = itemView.findViewById(R.id.chforAmount);
            itemImage = itemView.findViewById(R.id.chforimg);
            primaryPay = itemView.findViewById(R.id.chforPay);
        }
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageUrl = strings[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
