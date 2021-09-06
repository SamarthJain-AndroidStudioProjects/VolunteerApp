package com.example.volunteer.RecyclerAdapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volunteer.Account.Account;
import com.example.volunteer.Objects.User;
import com.example.volunteer.R;

import static com.example.volunteer.VolunteerAppCloudDatabase.*;

import java.util.ArrayList;

public class ViewVolunteers extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_volunteers);

        ArrayList<User> volunteers = new ArrayList<>();
        for(String uid : Account.currentOpportunity.getVolunteers().split(",")){
            volunteers.add(getUserInfo(uid));
        }
        RecyclerView recyclerView = findViewById(R.id.view_volunteers_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new ViewVolunteersRecyclerAdapter(volunteers));
    }
}
class ViewVolunteersRecyclerAdapter extends RecyclerView.Adapter<ViewVolunteersRecyclerAdapter.MyViewHolder> {
    private final ArrayList<User> volunteers;

    public ViewVolunteersRecyclerAdapter(ArrayList<User> volunteers) { this.volunteers = volunteers; }

    @NonNull
    @Override
    public ViewVolunteersRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewVolunteersRecyclerAdapter.MyViewHolder holder, int position) {
        holder.volunteerName.setText(volunteers.get(position).getName());
        holder.volunteerEmail.setText(volunteers.get(position).getEmail());
        holder.volunteerPhone.setText(volunteers.get(position).getPhone());
    }
    @Override public int getItemCount() { return volunteers.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView volunteerName;
        private final TextView volunteerEmail;
        private final TextView volunteerPhone;

        public MyViewHolder(@NonNull View view) {
            super(view);
            volunteerName = view.findViewById(R.id.volunteer_name);
            volunteerEmail = view.findViewById(R.id.volunteer_email);
            volunteerPhone = view.findViewById(R.id.volunteer_phone);
        }
    }
}