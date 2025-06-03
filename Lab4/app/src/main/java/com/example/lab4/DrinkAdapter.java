package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DrinkAdapter extends BaseAdapter {
    Context context;
    ArrayList<DrinkModel> drinkList;
    LayoutInflater inflater;

    public DrinkAdapter(Context context, ArrayList<DrinkModel> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.textview_drink_order, parent, false);
        }

        TextView drinkName = convertView.findViewById(R.id.drink_name);
        TextView drinkPrice = convertView.findViewById(R.id.drink_price);
        TextView drinkVolume = convertView.findViewById(R.id.drink_volume);
        TextView drinkIsBestSeller = convertView.findViewById(R.id.best_seller_badge);
        Button drinkRating = convertView.findViewById(R.id.drink_rating);
        ImageView drinkImage = convertView.findViewById(R.id.drink_image);

        DrinkModel drinkModel = drinkList.get(position);

        drinkName.setText(drinkModel.getName());
        drinkPrice.setText("$" + " " + drinkModel.getPrice());
        drinkVolume.setText(drinkModel.getVolume() + " ml");
        drinkRating.setText(String.valueOf(drinkModel.getStar()));
        drinkImage.setImageResource(drinkModel.getImageResourceId());
        drinkIsBestSeller.setVisibility(drinkModel.isBestseller() ? View.VISIBLE : View.GONE);

        Button addToCartButton = convertView.findViewById(R.id.drink_add_to_cart);
        addToCartButton.setOnClickListener(v -> {
            // Handle adding drink to cart
            Toast.makeText(context, drinkModel.getName() + " added to cart", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            String existingDrinks = prefs.getString("drinks", "");
            String updatedDrinks = existingDrinks + drinkModel.getName() + "\n";

            editor.putString("drinks", updatedDrinks);
            editor.apply();

            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });

        return convertView;
    }
}
