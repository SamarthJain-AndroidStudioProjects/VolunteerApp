package com.example.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volunteer.Objects.Opportunity;

import java.util.ArrayList;

public class MyOpportunities extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_opportunities);


        //recyclerview
    }
}
class MyOpportunitiesRecyclerAdapter extends RecyclerView.Adapter<MyOpportunitiesRecyclerAdapter.MyViewHolder> {
    private final ArrayList<Opportunity> opportunities;

    public MyOpportunitiesRecyclerAdapter(ArrayList<Opportunity> opportunities){
        this.opportunities = opportunities;
    }

    @NonNull
    @Override
    public MyOpportunitiesRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOpportunitiesRecyclerAdapter.MyViewHolder holder, int position) {
    }

    @Override public int getItemCount() { return opportunities.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public MyViewHolder(@NonNull View view) {
            super(view);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
