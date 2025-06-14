package com.example.lab3_v2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String tourists[] = {"Hà Nội", "Đà Lạt", "Vịnh Hạ Long", "Phú Quốc", "Nha Trang", "Hội An", "Huế", "Cần Thơ", "Đà Nẵng"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.device_listview);
        CustomAdapter customAdapter = new CustomAdapter(this, tourists);
        listView.setAdapter(customAdapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addButton = findViewById(R.id.add_btn);
        EditText addEditText = findViewById(R.id.add_edittext);
        addButton.setOnClickListener(v -> {
            String newDevice = addEditText.getText().toString();
            if (!newDevice.isEmpty()) {
                customAdapter.addItem(newDevice);
                addEditText.setText("");
            }
        });

    }
}