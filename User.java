package com.example.volunteer.Objects;

public class User {
    private String userID;
    private String name;
    private String email;
    private String phone;
    private String type;

    public User(){}
    public User(String userID, String name, String email, String phone, String type){
        setUserID(userID); setName(name); setEmail(email); setPhone(phone); setType(type);
    }
    public void setUserID(String userID) { this.userID = userID; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setType(String type) { this.type = type; }
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getType(){ return type; }

}
