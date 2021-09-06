package com.example.volunteer;

import androidx.annotation.NonNull;

import com.example.volunteer.Account.Account;
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

    default void addUserToFirebase() {
        FirebaseDatabase.getInstance().getReference("Users").child(Account.userID).setValue(new User(Account.userID, Account.name, Account.email, Account.phone, Account.type));
    }

    static void getUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
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

    default void addOpportunityToFirebase(Opportunity opportunity){
        ArrayList<Opportunity> opportunities = new ArrayList<>(getOpportunities());
        opportunities.add(opportunity);
        FirebaseDatabase.getInstance().getReference("Opportunities").setValue(opportunities);
        setOpportunities(opportunities);
    }

    static void getOpportunitiesFromFirebase(){
        FirebaseDatabase.getInstance().getReference("Opportunities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ArrayList<Opportunity> opportunities = new ArrayList<>(Objects.requireNonNull
                            (dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<Opportunity>>() {})));
                    setOpportunities(opportunities);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    default void deleteOpportunity(Opportunity opportunity){
        ArrayList<Opportunity> opportunities = new ArrayList<>(getOpportunities());
        opportunities.remove(opportunity);
        FirebaseDatabase.getInstance().getReference("Opportunities").removeValue();
        FirebaseDatabase.getInstance().getReference("Opportunities").setValue(opportunities);
        setOpportunities(opportunities);
    }
}