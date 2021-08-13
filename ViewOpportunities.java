package com.example.volunteer;

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

import com.example.volunteer.Objects.Opportunity;
import com.example.volunteer.Objects.User;

import java.util.ArrayList;
import static com.example.volunteer.VolunteerAppCloudDatabase.*;

public class ViewOpportunities extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_opportunities);

        ArrayList<Opportunity> opportunities = new ArrayList<>();
        for(Opportunity opportunity : getOpportunities()){
            boolean alreadyJoined = false;
            for(User volunteer : opportunity.getVolunteers()){
                if (volunteer.getUserID().equals(Account.userID)) { alreadyJoined = true; break; }
            }
            if(!alreadyJoined){
                if(Integer.parseInt(opportunity.getMaxVolunteers()) > opportunity.getVolunteers().size()){
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
            opportunityName = view.findViewById(R.id.view_opportunity_name);
            registerOrDelete = view.findViewById(R.id.right_button);
            if(Account.type.equals("Volunteer")){
                ((ImageButton) view.findViewById(R.id.opportunity_btn)).setImageResource(R.drawable.ic_baseline_register_24);
                registerOrDelete.setText(new String("Register"));
            }
            view.findViewById(R.id.opportunity_btn).setOnClickListener(this);
            view.findViewById(R.id.view_button).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.view_button){
                v.getContext().startActivity(new Intent(v.getContext(), OpportunityItemView.class));
            }
            else if(v.getId() == R.id.opportunity_btn){
                if(registerOrDelete.getText().toString().equals("Register")){
                    if(Account.type.equals("Volunteer")) {
                        Opportunity opportunity = opportunities.get(getAdapterPosition());
                        ArrayList<User> volunteers = new ArrayList<>(opportunities.get(getAdapterPosition()).getVolunteers());
                        volunteers.add(new User(Account.userID, Account.email, Account.phone, Account.type));
                        opportunity.setVolunteers(volunteers);
                        addVolunteer(opportunity);
                        Toast.makeText(v.getContext(), "Registered!", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(new Intent(v.getContext(), MyOpportunities.class));
                    }
                    else{
                        //delete Opportunity
                    }
                    opportunities.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                }
            }
        }
    }
}
