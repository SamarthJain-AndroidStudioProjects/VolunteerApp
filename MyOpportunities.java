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

public class MyOpportunities extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_opportunities);

        ArrayList<Opportunity> opportunities = new ArrayList<>();
        if(Account.type.equals("Volunteer")){
            for(Opportunity opportunity : getOpportunities()){
                boolean hasRegistered = false;
                for(String id : opportunity.getVolunteers().split(",")){
                    if(id.equals(Account.userID)){
                        hasRegistered = true; break;
                    }
                }
                if(hasRegistered) opportunities.add(opportunity);
            }
        }
        else{
            for(Opportunity opportunity : getOpportunities()){
                if(opportunity.getCreatorID().equals(Account.userID)){
                    opportunities.add(opportunity);
                }
            }
        }
        RecyclerView recyclerView = findViewById(R.id.my_opportunities_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyOpportunitiesRecyclerAdapter(opportunities));
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
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.opportunity_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOpportunitiesRecyclerAdapter.MyViewHolder holder, int position) {
        holder.opportunityName.setText(opportunities.get(position).getOpportunityName());
    }

    @Override public int getItemCount() { return opportunities.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Firebase {
        private final TextView opportunityName;

        public MyViewHolder(@NonNull View view) {
            super(view);
            opportunityName = view.findViewById(R.id.opportunity_item_opportunity_name);
            TextView registerOrDelete = view.findViewById(R.id.right_button);
            ImageButton opportunityBtn = view.findViewById(R.id.opportunity_item_opportunity_button);
            if(Account.type.equals("Volunteer")){
                registerOrDelete.setText(new String("Unregister"));
                opportunityBtn.setImageResource(R.drawable.ic_baseline_leave_24);
            }
            opportunityBtn.setOnClickListener(this);
            view.findViewById(R.id.opportunity_item_view_button).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.opportunity_item_view_button){
                Account.currentOpportunity = opportunities.get(getAdapterPosition());
                v.getContext().startActivity(new Intent(v.getContext(), OpportunityItemView.class));
            }
            else if(v.getId() == R.id.opportunity_item_opportunity_button){
                if(Account.type.equals("Volunteer")){
                    Opportunity opportunity = opportunities.get(getAdapterPosition());
                    opportunity.setVolunteers(opportunity.getVolunteers().replace(Account.userID + ",", ""));
                    addOpportunityToFirebase(opportunity);
                    Toast.makeText(v.getContext(), "Unregistered Opportunity", Toast.LENGTH_SHORT).show();
                }
                deleteOpportunity(opportunities.get(getAdapterPosition()));
                opportunities.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), getItemCount());
            }
        }
    }
}

//TODO: Max Volunteers >= 1
//TODO: Error Check Opportunity Name - No duplicates, only letters
//TODO: Account Settings
//TODO: View volunteers in OpportunityItemView - View info such as name, email, contact info