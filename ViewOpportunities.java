package com.example.volunteer.RecyclerAdapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volunteer.Account.Account;
import com.example.volunteer.Firebase;
import com.example.volunteer.Objects.Opportunity;
import com.example.volunteer.OpportunityItemView;
import com.example.volunteer.R;

import java.util.ArrayList;

import static com.example.volunteer.VolunteerAppCloudDatabase.*;

public class ViewOpportunities extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_opportunities);

        ArrayList<Opportunity> opportunities = new ArrayList<>();
        for(Opportunity opportunity : getOpportunities()){
            if(Integer.parseInt(opportunity.getMaxVolunteers()) > opportunity.getVolunteers().split(",").length){
                boolean alreadyRegistered = false;
                for(String id : opportunity.getVolunteers().split(",")){
                    if(id.equals(Account.userID)){
                        alreadyRegistered = true; break;
                    }
                }
                if(!alreadyRegistered){
                    opportunities.add(opportunity);
                }
            }
        }
        RecyclerView recyclerView = findViewById(R.id.view_opportunities_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new ViewOpportunitiesRecyclerAdapter(opportunities));
    }
}

class ViewOpportunitiesRecyclerAdapter extends RecyclerView.Adapter<ViewOpportunitiesRecyclerAdapter.MyViewHolder> {
    private final ArrayList<Opportunity> opportunities;

    public ViewOpportunitiesRecyclerAdapter(ArrayList<Opportunity> opportunities){ this.opportunities = opportunities; }

    @NonNull
    @Override
    public ViewOpportunitiesRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.opportunity_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOpportunitiesRecyclerAdapter.MyViewHolder holder, int position) {
        holder.opportunityName.setText(opportunities.get(position).getOpportunityName());
    }

    @Override public int getItemCount() { return opportunities.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Firebase {
        private final TextView opportunityName;
        private final TextView registerOrDelete;

        public MyViewHolder(@NonNull View view) {
            super(view);
            opportunityName = view.findViewById(R.id.opportunity_item_opportunity_name);
            registerOrDelete = view.findViewById(R.id.right_button);
            if(Account.type.equals("Volunteer")){
                ((ImageButton) view.findViewById(R.id.opportunity_item_opportunity_button)).setImageResource(R.drawable.ic_baseline_register_24);
                registerOrDelete.setText(new String("Register"));
            }
            view.findViewById(R.id.opportunity_item_opportunity_button).setOnClickListener(this);
            view.findViewById(R.id.opportunity_item_view_button).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.opportunity_item_view_button){
                Account.currentOpportunity = opportunities.get(getAdapterPosition());
                v.getContext().startActivity(new Intent(v.getContext(), OpportunityItemView.class));
            }
            else if(v.getId() == R.id.opportunity_item_opportunity_button){
                if(registerOrDelete.getText().toString().equals("Register")){
                    if(Account.type.equals("Volunteer")) {
                        Opportunity opportunity = opportunities.get(getAdapterPosition());
                        opportunity.setVolunteers(opportunity.getVolunteers().concat(Account.userID).concat(","));
                        addOpportunityToFirebase(opportunity);
                        Toast.makeText(v.getContext(), "Registered!", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(new Intent(v.getContext(), MyOpportunities.class));
                    }
                    deleteOpportunity(opportunities.get(getAdapterPosition()));
                    opportunities.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                }
            }
        }
    }
}
