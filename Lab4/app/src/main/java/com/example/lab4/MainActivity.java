package com.example.lab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button foodOrderButton = findViewById(R.id.order_food_button);
        foodOrderButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FoodOrderActivity.class));
        });

        Button drinkOrderButton = findViewById(R.id.order_drink_button);
        drinkOrderButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DrinkOrderActivity.class));
        });

        SharedPreferences prefs = getSharedPreferences("cart_prefs", MODE_PRIVATE);
        String drinkOrders = prefs.getString("drinks", "");
        System.out.println("Drink Orders: " + drinkOrders);
        for (String order : drinkOrders.split("\n")) {
            if (!order.isEmpty()) {
                Toast.makeText(this, "Drink Order: " + order, Toast.LENGTH_SHORT).show();
            }
        }
        TextView foodTextView = findViewById(R.id.food_text);
        TextView drinkTextView = findViewById(R.id.drink_text);
        TextView separatorTextView = findViewById(R.id.separator);
        drinkTextView.setText("Drink Orders:\n" + drinkOrders);
        drinkTextView.setVisibility(drinkOrders.isEmpty() ? View.GONE : View.VISIBLE);
    }
}