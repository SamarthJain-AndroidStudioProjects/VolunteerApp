package com.example.volunteer.Account;

import com.example.volunteer.Objects.Opportunity;

public class Account {
    public static String userID;
    public static String type;
    public static String email;
    public static String phone;
    public static String name;
    public static Opportunity currentOpportunity;
    public static boolean botCheck = false;
    public static void reset(){ botCheck = false; }
}
