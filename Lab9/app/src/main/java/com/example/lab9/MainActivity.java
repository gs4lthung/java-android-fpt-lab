package com.example.lab9;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler database;
    ArrayList<JobModel> jobModels = new ArrayList<>();

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

        database = DatabaseHandler.getInstance(getApplicationContext());
        Cursor cursor = database.getJobs();
        Toast.makeText(this, "Number of jobs: " + cursor.getCount(), Toast.LENGTH_SHORT).show();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            jobModels.add(new JobModel(id, title, description));
        }

        ListView jobList = findViewById(R.id.listView);
        JobAdapter jobAdapter = new JobAdapter(this, jobModels);
        jobList.setAdapter(jobAdapter);

        Button addButton = findViewById(R.id.buttonAdd);
        EditText titleInput = findViewById(R.id.editText);
        EditText descriptionInput = findViewById(R.id.editTextDescription);
        addButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            database.addJob(title, description);
            jobModels.clear();
            Cursor newCursor = database.getJobs();
            while (newCursor.moveToNext()) {
                int id = newCursor.getInt(0);
                String jobTitle = newCursor.getString(1);
                String jobDescription = newCursor.getString(2);
                jobModels.add(new JobModel(id, jobTitle, jobDescription));
            }
            jobAdapter.notifyDataSetChanged();
            titleInput.setText("");
            descriptionInput.setText("");
        });
    }
}