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
        FirebaseDatabase.getInstance().getReference("Users").child(Account.userID).setValue(new User(email, phone, type));
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

    default void createOpportunity(String name, String description, String address, String startDate, String startTime, String endTime, String volHours) {
        ArrayList<Opportunity> userOpportunities = new ArrayList<>();
        for(Opportunity opportunity : getOpportunities()){
            if(opportunity.getCreatorID().equals(Account.userID)) userOpportunities.add(opportunity);
        }
        userOpportunities.add(new Opportunity(Account.userID, name, description, address, startDate, startTime, endTime, volHours));
        FirebaseDatabase.getInstance().getReference("Opportunities").child(Account.userID).setValue(userOpportunities);
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
}