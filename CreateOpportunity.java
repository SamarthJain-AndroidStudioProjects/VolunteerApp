package com.example.volunteer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateOpportunity extends AppCompatActivity implements Firebase{
    private String name;
    private String description;
    private String address;
    private String startDate;
    private String startTime;
    private String endTime;
    private String volHours;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_opportunity);

        name = ((EditText) findViewById(R.id.Organization_Name)).getText().toString().trim();
        description = ((EditText) findViewById(R.id.Description)).getText().toString().trim();
        address = ((EditText) findViewById(R.id.Address)).getText().toString().trim();
        startDate = ((EditText) findViewById(R.id.date_picker_actions)).getText().toString().trim();
        startTime = ((EditText) findViewById(R.id.Starting_Time)).getText().toString().trim();
        endTime = ((EditText) findViewById(R.id.Ending_Time)).getText().toString().trim();
        volHours = ((EditText) findViewById(R.id.Num_Vol_Hours)).getText().toString().trim();

        findViewById(R.id.Enter).setOnClickListener(v -> {
            if(!name.isEmpty() && !description.isEmpty() && !address.isEmpty() && !startDate.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {
                if(volHours.isEmpty()) volHours = "Optional";
                createOpportunity(name, description, address, startDate, startTime, endTime, volHours);
            }
            else Toast.makeText(getApplicationContext(), "Fill all required fields", Toast.LENGTH_LONG).show();
        });
    }
}
