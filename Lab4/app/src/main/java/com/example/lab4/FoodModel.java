package com.example.lab4;

public class FoodModel {
    private String name;
    private String calories;
    private String price;
    private String star;
    private boolean isBestseller;
    private int imageResourceId;

    public FoodModel(String name, String calories, String price, String star, boolean isBestseller, int imageResourceId) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.star = star;
        this.isBestseller = isBestseller;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getCalories() {
        return calories;
    }

    public String getPrice() {
        return price;
    }

    public String getStar() {
        return star;
    }

    public boolean isBestseller() {
        return isBestseller;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
