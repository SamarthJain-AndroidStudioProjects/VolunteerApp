package com.example.volunteer;

public class Opportunity {
    private String name, description, address, startDate, startTime, endTime, volHours;

    public Opportunity(){}
    public Opportunity(String n, String d, String a, String sD, String sT, String eT, String vH) {
        setName(n); setDescription(d); setAddress(a); setStartDate(sD);
        setStartTime(sT); setEndTime(eT); setVolHours(vH);
    }

    public void setName(String n) { name = n;}
    public void setDescription(String d) { description = d; }
    public void setAddress(String a) { address = a; }
    public void setStartDate(String sD) { startDate = sD; }
    public void setStartTime(String sT) { startTime = sT; }
    public void setEndTime(String eT) { endTime = eT; }
    public void setVolHours(String vH) { volHours = vH; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public String getAddress(){ return address; }
    public String getStartDate(){ return startDate; }
    public String getStartTime(){ return startTime; }
    public String getEndTime(){ return endTime; }
    public String getVolHours(){ return volHours; }
}