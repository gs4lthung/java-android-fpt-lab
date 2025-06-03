package com.example.lab4;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DrinkOrderActivity extends AppCompatActivity {
    ArrayList<DrinkModel> drinkList;
    ListView drinkListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.actibity_drink_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drink), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drinkList = new ArrayList<>();
        drinkList.add(new DrinkModel("Coke", "150 calories", "1.99", "4.5", true, R.drawable.drink_1));
        drinkList.add(new DrinkModel("Lemonade", "120 calories", "2.49", "4.0", false, R.drawable.drink_1));
        drinkList.add(new DrinkModel("Iced Tea", "80 calories", "2.99", "4.2", true, R.drawable.drink_1));
        drinkList.add(new DrinkModel("Coffee", "5 calories", "1.49", "4.8", true, R.drawable.drink_1));
        drinkList.add(new DrinkModel("Orange Juice", "110 calories", "3.49", "4.6", false, R.drawable.drink_1));
        drinkList.add(new DrinkModel("Water", "0 calories", "0.99", "4.3", true, R.drawable.drink_1));

        drinkListView = findViewById(R.id.drink_list);
        DrinkAdapter drinkAdapter = new DrinkAdapter(this, drinkList);
        drinkListView.setAdapter(drinkAdapter);
    }
}
