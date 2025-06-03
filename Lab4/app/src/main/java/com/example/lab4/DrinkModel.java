package com.example.lab4;

public class DrinkModel {
    private String name;
    private String volume;
    private String price;
    private String star;
    private boolean isBestseller;
    private int imageResourceId;

    public DrinkModel(String name, String volume, String price, String star, boolean isBestseller, int imageResourceId) {
        this.name = name;
        this.volume = volume;
        this.price = price;
        this.star = star;
        this.isBestseller = isBestseller;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getVolume() {
        return volume;
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
