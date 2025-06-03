package com.example.lab4;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodAdapter extends BaseAdapter {
    Context context;
    ArrayList<FoodModel> foodList;
    LayoutInflater inflater;

    public FoodAdapter(Context context, ArrayList<FoodModel> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.textview_food_order, parent, false);
        }

        TextView foodName = convertView.findViewById(R.id.food_name);
        TextView foodPrice = convertView.findViewById(R.id.food_price);
        TextView foodCalories = convertView.findViewById(R.id.food_calories);
        Button foodRating = convertView.findViewById(R.id.food_rating);
        TextView foodIsBestSeller = convertView.findViewById(R.id.best_seller_badge);
        ImageView foodImage = convertView.findViewById(R.id.food_image);

        FoodModel foodModel = foodList.get(position);

        foodName.setText(foodModel.getName());
        foodPrice.setText("$" + " " + foodModel.getPrice());
        foodCalories.setText(foodModel.getCalories());
        foodRating.setText("⭐" + " " + foodModel.getStar());
        foodIsBestSeller.setVisibility(foodModel.isBestseller() ? View.VISIBLE : View.GONE);
        foodImage.setImageResource(foodModel.getImageResourceId());

        Button addToCartButton = convertView.findViewById(R.id.food_add_to_cart);
        addToCartButton.setOnClickListener(v -> {
            // Handle the button click event
            Toast.makeText(context, foodModel.getName() + " added to cart", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("food_text", foodModel.getName());
            context.startActivity(intent);
        });

        return convertView;
    }
}
