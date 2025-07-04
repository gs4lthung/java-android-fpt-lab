package com.example.pe;

import java.util.ArrayList;

public class GroupModel {
    private String id;
    private String title;
    private ArrayList<UserModel> users;

    public GroupModel(String id, String title) {
        this.id = id;
        this.title = title;
        this.users = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<UserModel> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserModel> users) {
        this.users = users;
    }
}
