package com.example.volunteer;

public class User {
    private String email, phone, type;

    public User(){}
    public User(String email, String phone, String type){
     setEmail(email); setPhone(phone); setType(type);
    }

    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setType(String type) { this.type = type; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getType(){ return type; }
}
