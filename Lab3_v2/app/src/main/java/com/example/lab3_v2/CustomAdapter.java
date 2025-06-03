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

        public void addItem(String device) {
            String[] newDevices = new String[devices.length + 1];
            System.arraycopy(devices, 0, newDevices, 0, devices.length);
            newDevices[devices.length] = device;
            devices = newDevices;
            notifyDataSetChanged();
        }

        public void deleteItem(int position) {
            if (position < 0 || position >= devices.length) return;
            String[] newDevices = new String[devices.length - 1];
            for (int i = 0, j = 0; i < devices.length; i++) {
                if (i != position) {
                    newDevices[j++] = devices[i];
                }
            }
            devices = newDevices;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.activity_listview, parent, false);
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

            textView.setText(devices[position]);
            editText.setText(devices[position]);

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
                    devices[position] = editText.getText().toString();
                    editingPosition = -1;
                } else {
                    editingPosition = position;
                }
                notifyDataSetChanged();
            });

            return convertView;
        }
    }
