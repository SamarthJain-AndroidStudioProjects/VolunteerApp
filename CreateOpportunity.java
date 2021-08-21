package com.example.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.volunteer.Account.Account;
import com.example.volunteer.Objects.Opportunity;

import static com.example.volunteer.VolunteerAppCloudDatabase.*;


public class CreateOpportunity extends AppCompatActivity implements Firebase{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_opportunity);

        findViewById(R.id.create_btn).setOnClickListener(v -> {
            String opportunityName = ((EditText) findViewById(R.id.opportunity_name)).getText().toString().trim();
            String organizerName = ((EditText) findViewById(R.id.organization_name)).getText().toString().trim();
            String description = ((EditText) findViewById(R.id.description)).getText().toString().trim();
            String address = ((EditText) findViewById(R.id.address)).getText().toString().trim();
            String maximumVolunteers = ((EditText) findViewById(R.id.maximum_volunteers)).getText().toString().trim();
            String startDate = ((EditText) findViewById(R.id.start_date)).getText().toString().trim();
            String startTime = ((EditText) findViewById(R.id.start_time)).getText().toString().trim();
            String endTime = ((EditText) findViewById(R.id.end_time)).getText().toString().trim();

            if(!opportunityName.isEmpty() && !organizerName.isEmpty() && !description.isEmpty() && !address.isEmpty()
                    && !maximumVolunteers.isEmpty() && !startDate.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {
                boolean sameName = false;
                for(Opportunity opportunity : getOpportunities()){
                    if(opportunity.getOpportunityName().equals(opportunityName)){
                        sameName = true; break;
                    }
                }
                if(!sameName){
                    addOpportunityToFirebase(new Opportunity(Account.userID, opportunityName, organizerName, description, address,
                            maximumVolunteers, startDate, startTime, endTime, ""));
                    Toast.makeText(getApplicationContext(), "Opportunity Created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                }
                else Toast.makeText(getApplicationContext(), "Opportunity Name Taken!", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "Fill All Fields!", Toast.LENGTH_LONG).show();
        });
    }
}