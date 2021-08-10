package com.example.volunteer;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public interface Firebase {

    default void addUser(String email, String phone, String type) {
        FirebaseDatabase.getInstance().getReference("Users").child(Account.userID)
                .setValue(new User(email, phone, type));
    }
    default void getUsers(UserCallback userCallback) {
        FirebaseDatabase.getInstance().getReference("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<User> users = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        users.add(child.getValue(User.class));
                    }
                    userCallback.getUserData(users);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    default void createOpportunity(String name, String description, String address, String startDate, String startTime, String endTime, String volHours) {
        getOpportunities(new OpportunityCallback() {
            @Override
            public void getOpportunities(ArrayList<Opportunity> opportunities) {
                ArrayList<Opportunity> userOpportunities = new ArrayList<>();
                for(Opportunity opportunity : opportunities){
                    if(opportunity.getCreatorID().equals(Account.userID)) userOpportunities.add(opportunity);
                }
                userOpportunities.add(new Opportunity(Account.userID, name, description, address, startDate, startTime, endTime, volHours));
                FirebaseDatabase.getInstance().getReference("Opportunities").child(Account.userID).setValue(userOpportunities);
            }
        });
    }

    default void getOpportunities(OpportunityCallback opportunityCallback){
        FirebaseDatabase.getInstance().getReference("Opportunities")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            ArrayList<Opportunity> opportunities = new ArrayList<>();
                            for(DataSnapshot child : dataSnapshot.getChildren()){
                                opportunities.addAll(Objects.requireNonNull(child.getValue(new GenericTypeIndicator<ArrayList<Opportunity>>(){})));
                            }
                            opportunityCallback.getOpportunities(opportunities);
                        }
                    }
                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
    interface UserCallback { void getUserData(ArrayList<User> list); }
    interface OpportunityCallback { void getOpportunities(ArrayList<Opportunity> opportunities); }
}

