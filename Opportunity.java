package com.example.volunteer.Objects;

import androidx.annotation.Nullable;

public class Opportunity {
    private String opportunityName;
    private String organizerName;
    private String description;
    private String address;
    private String maxVolunteers;
    private String startDate;
    private String startTime;
    private String endTime;
    private String creatorID;
    private String volunteers;

    public Opportunity(){}
    public Opportunity(String creatorID, String opportunityName, String organizerName, String description, String address, String maxVolunteers, String startDate, String startTime, String endTime, String volunteers) {
        setCreatorID(creatorID); setOpportunityName(opportunityName); setOrganizerName(organizerName); setDescription(description); setAddress(address);
        setMaxVolunteers(maxVolunteers); setStartDate(startDate); setStartTime(startTime); setEndTime(endTime); setVolunteers(volunteers);
    }
    public String getOpportunityName() { return opportunityName; }
    public void setOpportunityName(String opportunityName) { this.opportunityName = opportunityName; }
    public String getOrganizerName() { return organizerName; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getMaxVolunteers() { return maxVolunteers; }
    public void setMaxVolunteers(String maxVolunteers) { this.maxVolunteers = maxVolunteers; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getCreatorID() { return creatorID; }
    public void setCreatorID(String creatorID) { this.creatorID = creatorID; }
    public String getVolunteers() { return volunteers; }
    public void setVolunteers(String volunteers) { this.volunteers = volunteers; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Opportunity){
            Opportunity opportunity = (Opportunity) obj;
            return this.getOpportunityName().equals(opportunity.getOpportunityName());
        }
        return false;
    }
}