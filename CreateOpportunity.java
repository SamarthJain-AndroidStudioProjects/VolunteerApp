package com.example.volunteer;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class CreateOpportunity extends AppCompatActivity implements Firebase{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_opportunity);

        findViewById(R.id.Enter).setOnClickListener(v -> {
            String name = ((EditText) findViewById(R.id.Organization_Name)).getText().toString().trim();
            String description = ((EditText) findViewById(R.id.Description)).getText().toString().trim();
            String address = ((EditText) findViewById(R.id.Address)).getText().toString().trim();
            String startDate = ((EditText) findViewById(R.id.date_picker_actions)).getText().toString().trim();
            String startTime = ((EditText) findViewById(R.id.Starting_Time)).getText().toString().trim();
            String endTime = ((EditText) findViewById(R.id.Ending_Time)).getText().toString().trim();
            String volHours = ((EditText) findViewById(R.id.Num_Vol_Hours)).getText().toString().trim();

            if(!name.isEmpty() && !description.isEmpty() && !address.isEmpty() && !startDate.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {
                if(volHours.isEmpty()) volHours = "Optional";
                createOpportunity(name, description, address, startDate, startTime, endTime, volHours);
                Toast.makeText(getApplicationContext(), "Opportunity Created!", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "Fill all required fields", Toast.LENGTH_LONG).show();
        });
    }
}