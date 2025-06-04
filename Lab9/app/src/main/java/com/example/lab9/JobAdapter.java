package com.example.lab9;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class JobAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;

    ArrayList<JobModel> jobList;

    int editingPosition = -1;

    public JobAdapter(Context context, ArrayList<JobModel> jobList) {
        this.context = context;
        this.jobList = jobList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return jobList != null ? jobList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return jobList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return jobList.get(position).getId();
    }

    public void updateItem(int position, JobModel job) {
        if (position >= 0 && position < jobList.size()) {
            jobList.set(position, job);
        }
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < jobList.size()) {
            jobList.remove(position);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.job_detail, parent, false);
        }
        TextView title = convertView.findViewById(R.id.job_title);
        EditText editTitle = convertView.findViewById(R.id.job_title_input);
        TextView description = convertView.findViewById(R.id.job_description);
        EditText editDescription = convertView.findViewById(R.id.job_description_input);
        JobModel job = jobList.get(position);
        title.setText(job.getTitle());
        description.setText(job.getDescription());

        DatabaseHandler database = DatabaseHandler.getInstance(context.getApplicationContext());

        Button editButton = convertView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(v -> {
            if (editingPosition == position) {
                if (editTitle.getText().toString().isEmpty() || editDescription.getText().toString().isEmpty()) {
                    editTitle.setError("Title cannot be empty");
                    editDescription.setError("Description cannot be empty");
                    return;
                }
                updateItem(position, new JobModel(
                        job.getId(),
                        editTitle.getText().toString(),
                        editDescription.getText().toString()
                ));
                database.updateJob(job.getId(), editTitle.getText().toString(), editDescription.getText().toString());
                notifyDataSetChanged();
                title.setVisibility(View.VISIBLE);
                editTitle.setVisibility(View.GONE);
                description.setVisibility(View.VISIBLE);
                editDescription.setVisibility(View.GONE);
                editButton.setText("Edit");
                editingPosition = -1;
            } else {
                editingPosition = position;
                title.setVisibility(View.GONE);
                editTitle.setVisibility(View.VISIBLE);
                editTitle.setText(job.getTitle());
                description.setVisibility(View.GONE);
                editDescription.setVisibility(View.VISIBLE);
                editDescription.setText(job.getDescription());
                editButton.setText("Save");
            }
        });

        Button deleteButton = convertView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Job")
                    .setMessage("Are you sure you want to delete this job?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        deleteItem(position);
                        database.deleteJob(job.getId());
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        return convertView;
    }
}
