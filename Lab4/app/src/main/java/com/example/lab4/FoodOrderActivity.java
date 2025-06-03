package com.example.lab4;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FoodOrderActivity extends AppCompatActivity {

    ArrayList<FoodModel> foodList;
    ListView foodListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.food), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        foodList = new ArrayList<>();
        foodList.add(new FoodModel("Pizza", "300 calories", "8.99", "4.5", true, R.drawable.food_1));
        foodList.add(new FoodModel("Burger", "500 calories", "5.99", "4.0", false, R.drawable.food_1));
        foodList.add(new FoodModel("Pasta", "400 calories", "7.99", "4.2", true, R.drawable.food_1));
        foodList.add(new FoodModel("Salad", "150 calories", "4.99", "4.8", true, R.drawable.food_1));
        foodList.add(new FoodModel("Sushi", "250 calories", "9.99", "4.6", false, R.drawable.food_1));
        foodList.add(new FoodModel("Tacos", "350 calories", "6.99", "4.3", true, R.drawable.food_1));
        foodList.add(new FoodModel("Steak", "700 calories", "14.99", "4.9", false, R.drawable.food_1));
        foodList.add(new FoodModel("Ice Cream", "200 calories", "3.99", "4.1", true, R.drawable.food_1));
        foodList.add(new FoodModel("Fries", "400 calories", "2.99", "4.4", false, R.drawable.food_1));
        foodList.add(new FoodModel("Sandwich", "350 calories", "5.49", "4.0", true, R.drawable.food_1));

        foodListView = findViewById(R.id.food_list);
        FoodAdapter foodAdapter = new FoodAdapter(this, foodList);
        foodListView.setAdapter(foodAdapter);
    }
}
