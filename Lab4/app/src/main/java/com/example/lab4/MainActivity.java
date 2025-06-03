package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            // Start the FoodOrderActivity when the button is clicked
            startActivity(new Intent(MainActivity.this, FoodOrderActivity.class));
        });

        String foodText = getIntent().getStringExtra("food_text");
        TextView foodTextView = findViewById(R.id.food_text);
        if (foodText != null) {
            foodTextView.setText(foodText);
            foodTextView.setVisibility(View.VISIBLE);
        } else {
            foodTextView.setVisibility(View.GONE);
        }
    }
}