package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String devices[];
    LayoutInflater layoutInflater;
    int editingPosition = -1; // -1 means no item is being edited

    public CustomAdapter(Context context, String[] devices) {
        this.context = context;
        this.devices = devices;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return devices.length;
    }

    @Override
    public Object getItem(int position) {
        return devices[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_listview, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.device_textview);
        EditText editText=convertView.findViewById(R.id.device_edittext);
        Button updateButton=convertView.findViewById(R.id.update_btn);

        textView.setText(devices[position]);
        editText.setText(devices[position]);

        return convertView;
    }

    public void updateItem(int position, String newValue) {
        if (position <= 0 || position > devices.length - 1) {
            return;
        }
        devices[position] = newValue;
        notifyDataSetChanged();
    }
}
