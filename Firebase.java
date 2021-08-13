package com.example.volunteer;

import androidx.annotation.NonNull;

import com.example.volunteer.Objects.Opportunity;
import com.example.volunteer.Objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import static com.example.volunteer.VolunteerAppCloudDatabase.*;

public interface Firebase {

    default void addUser(String email, String phone, String type) {
        FirebaseDatabase.getInstance().getReference("Users").child(Account.userID).setValue(new User(Account.userID, email, phone, type));
    }
    static void getUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<User> users = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) users.add(child.getValue(User.class));
                    setUsers(users);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    default void addOpportunity(Opportunity newOpportunity) {
        ArrayList<Opportunity> userOpportunities = new ArrayList<>();
        for(Opportunity opportunity : getOpportunities()){
            if(opportunity.getCreatorID().equals(Account.userID)) userOpportunities.add(opportunity);
        }
        userOpportunities.add(newOpportunity);
        FirebaseDatabase.getInstance().getReference("Opportunities").child(newOpportunity.getCreatorID()).removeValue();
        FirebaseDatabase.getInstance().getReference("Opportunities").child(newOpportunity.getCreatorID()).setValue(userOpportunities);
    }

    static void getOpportunitiesFromFirebase(){
        FirebaseDatabase.getInstance().getReference("Opportunities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ArrayList<Opportunity> opportunities = new ArrayList<>();
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        opportunities.addAll(Objects.requireNonNull(child.getValue(new GenericTypeIndicator<ArrayList<Opportunity>>(){})));
                    }
                    setOpportunities(opportunities);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    default void addVolunteer(Opportunity opportunity){
        ArrayList<Opportunity> opportunities = new ArrayList<>(getOpportunities());
        for(int i = 0; i < opportunities.size(); i++){
            if(opportunities.get(i).getOpportunityName().equals(opportunity.getOpportunityName())
                    && opportunities.get(i).getCreatorID().equals(opportunity.getCreatorID())){
                opportunities.set(i, opportunity); break;
            }
        }
        FirebaseDatabase.getInstance().getReference("Opportunities").child(opportunity.getCreatorID()).removeValue();
        FirebaseDatabase.getInstance().getReference("Opportunities").child(opportunity.getCreatorID()).setValue(opportunities);
    }
}