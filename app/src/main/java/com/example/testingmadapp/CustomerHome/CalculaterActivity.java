package com.example.testingmadapp.CustomerHome;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testingmadapp.R;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class CalculaterActivity extends AppCompatActivity {

    private EditText primaryPaymentInput;
    private EditText daysInput;
    private EditText amountPerKmPriceInput;
    private EditText distanceCoveredInput;
    private EditText discountInput;
    private TextView resultOutput;
    private Button calculateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculater);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize UI components
        primaryPaymentInput = findViewById(R.id.primary_payment_input);
        daysInput = findViewById(R.id.days_input);
        amountPerKmPriceInput = findViewById(R.id.amount_per_km_price_input);
        distanceCoveredInput = findViewById(R.id.distance_covered_input);
        discountInput = findViewById(R.id.discount_input);
        resultOutput = findViewById(R.id.result_output);
        calculateButton = findViewById(R.id.button_calculater_enter);

        // Set up button click listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalPayment();
            }
        });
    }

    private void calculateTotalPayment() {
        // Get input values
        String primaryPaymentStr = primaryPaymentInput.getText().toString();
        String daysStr = daysInput.getText().toString();
        String amountPerKmPriceStr = amountPerKmPriceInput.getText().toString();
        String distanceCoveredStr = distanceCoveredInput.getText().toString();
        String discountStr = discountInput.getText().toString();

        // Validate input
        if (TextUtils.isEmpty(primaryPaymentStr) || TextUtils.isEmpty(daysStr) || TextUtils.isEmpty(amountPerKmPriceStr) ||
                TextUtils.isEmpty(distanceCoveredStr) || TextUtils.isEmpty(discountStr)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double primaryPayment = Double.parseDouble(primaryPaymentStr);
        int days = Integer.parseInt(daysStr);
        double amountPerKmPrice = Double.parseDouble(amountPerKmPriceStr);
        double distanceCovered = Double.parseDouble(distanceCoveredStr);
        double discount = Double.parseDouble(discountStr);

        // Calculate total payment
        double distanceCostPerDay = distanceCovered * amountPerKmPrice;
        double totalCostBeforeDiscount = primaryPayment + (distanceCostPerDay * days);
        double totalPayment = totalCostBeforeDiscount * (1 - (discount / 100));

        // all cal result akka display karanno
        resultOutput.setText(String.format("Total Payment: $%.2f", totalPayment));
    }
}



