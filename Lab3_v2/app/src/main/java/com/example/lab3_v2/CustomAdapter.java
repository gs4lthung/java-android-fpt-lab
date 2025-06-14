package com.example.lab3_v2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String tourists[];
    LayoutInflater layoutInflater;
    int editingPosition = -1; // -1 means no item is being edited

    public CustomAdapter(Context context, String[] tourists) {
        this.context = context;
        this.tourists = tourists;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return tourists.length;
    }

    @Override
    public Object getItem(int position) {
        return tourists[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String device) {
        String[] newTourists = new String[tourists.length + 1];
        System.arraycopy(tourists, 0, newTourists, 0, tourists.length);
        newTourists[tourists.length] = device;
        tourists = newTourists;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (position < 0 || position >= tourists.length) return;
        String[] newTourists = new String[tourists.length - 1];
        for (int i = 0, j = 0; i < tourists.length; i++) {
            if (i != position) {
                newTourists[j++] = tourists[i];
            }
        }
        tourists = newTourists;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_view, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.device_textview);
        EditText editText = convertView.findViewById(R.id.device_edittext);
        Button updateButton = convertView.findViewById(R.id.update_btn);

        Button deleteButton = convertView.findViewById(R.id.delete_btn);
        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Device")
                    .setMessage("Are you sure you want to delete this device?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteItem(position);
                            if (editingPosition == position) {
                                editingPosition = -1; // Reset editing position if deleted
                            }
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        textView.setText(tourists[position]);
        editText.setText(tourists[position]);

        if (position == editingPosition) {
            textView.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            updateButton.setText("Save");
        } else {
            textView.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            updateButton.setText("Update");
        }

        updateButton.setOnClickListener(v -> {
            if (position == editingPosition) {
                tourists[position] = editText.getText().toString();
                editingPosition = -1;
            } else {
                editingPosition = position;
            }
            notifyDataSetChanged();
        });

        return convertView;
    }
}
