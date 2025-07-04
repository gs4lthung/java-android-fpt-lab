package com.example.pe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class UserSelectAdapter extends RecyclerView.Adapter<UserSelectAdapter.ViewHolder> {

    private final List<UserModel> users;
    private final ArrayList<UserModel> selectedUsers = new ArrayList<>();

    public UserSelectAdapter(List<UserModel> users) {
        this.users = users;
    }

    public ArrayList<UserModel> getSelectedUsers() {
        return selectedUsers;
    }

    public void clearSelection() {
        selectedUsers.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSelectAdapter.ViewHolder holder, int position) {
        UserModel user = users.get(position);
        holder.name.setText(user.getName());
        holder.phone.setText(user.getPhone());

        holder.checkBox.setOnCheckedChangeListener(null); // Reset listener before setting state
        holder.checkBox.setChecked(selectedUsers.contains(user));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) selectedUsers.add(user);
            else selectedUsers.remove(user);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            phone = itemView.findViewById(R.id.textPhone);
            checkBox = itemView.findViewById(R.id.checkboxUser);
        }
    }
}
