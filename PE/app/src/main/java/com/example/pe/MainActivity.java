package com.example.pe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private Button btnCreateGroup;
    private UserSelectAdapter userAdapter;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = DatabaseHandler.getInstance(this);
//        dbHandler.addUser(new UserModel(
//                "1", "Lam Tien Hung", "0832428279"
//        ));

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        btnCreateGroup = findViewById(R.id.btnCreateGroup);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        List<UserModel> users = dbHandler.getAllUsers();  // You'll need to implement this
        userAdapter = new UserSelectAdapter(users);
        recyclerViewUsers.setAdapter(userAdapter);

        btnCreateGroup.setOnClickListener(v -> showCreateGroupDialog());
    }

    private void showCreateGroupDialog() {
        ArrayList<UserModel> selectedUsers = userAdapter.getSelectedUsers();

        if (selectedUsers.isEmpty()) {
            Toast.makeText(this, "Please select at least one user", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText input = new EditText(this);
        input.setHint("Group name");

        new AlertDialog.Builder(this)
                .setTitle("Create Group")
                .setView(input)
                .setPositiveButton("Create", (dialog, which) -> {
                    String groupName = input.getText().toString().trim();
                    if (!groupName.isEmpty()) {
                        createGroup(groupName, selectedUsers);
                    } else {
                        Toast.makeText(this, "Group name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void createGroup(String groupName, List<UserModel> users) {
        String groupId = "group" + System.currentTimeMillis();
        GroupModel group = new GroupModel(groupId, groupName);
        dbHandler.addGroup(group);
        for (UserModel user : users) {
            dbHandler.addUserToGroup(groupId, user);
        }
        Toast.makeText(this, "Group created successfully", Toast.LENGTH_SHORT).show();
        userAdapter.clearSelection();
    }
}
