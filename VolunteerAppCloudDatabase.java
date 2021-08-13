package com.example.volunteer;

import com.example.volunteer.Objects.Opportunity;
import com.example.volunteer.Objects.User;

import java.util.ArrayList;

public class VolunteerAppCloudDatabase implements Firebase{
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Opportunity> opportunities = new ArrayList<>();

    public static void setUsers(ArrayList<User> getUsers){
        users.clear(); users.addAll(getUsers);
    }
    public static void setOpportunities(ArrayList<Opportunity> getOpportunities){
        opportunities.clear(); opportunities.addAll(getOpportunities);
    }
    public static ArrayList<User> getUsers(){
        Firebase.getUsersFromFirebase(); return users;
    }
    public static ArrayList<Opportunity> getOpportunities(){
        Firebase.getOpportunitiesFromFirebase(); return opportunities;
    }
    public static void initializeDatabase(){ getOpportunities(); getUsers(); }
}
