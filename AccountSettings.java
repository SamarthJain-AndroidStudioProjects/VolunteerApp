package com.example.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.volunteer.Account.Account;
import com.example.volunteer.Objects.Opportunity;
import static com.example.volunteer.VolunteerAppCloudDatabase.*;

import java.util.ArrayList;

public class AccountSettings extends AppCompatActivity implements Firebase, View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        findViewById(R.id.change_name_btn).setOnClickListener(this);
        findViewById(R.id.change_phone_btn).setOnClickListener(this);
        findViewById(R.id.delete_account_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = ((EditText) findViewById(R.id.change_name)).getText().toString().trim();
        String phone = ((EditText) findViewById(R.id.change_phone)).getText().toString().trim();

        if(view.getId() == R.id.change_name_btn){
            if(!name.isEmpty()) {
                changeName(name);
                Toast.makeText(getApplicationContext(), "Account Name Changed!", Toast.LENGTH_LONG).show();
                ((EditText) findViewById(R.id.change_name)).setText("");
            }
            else Toast.makeText(getApplicationContext(), "Enter name to be changed!", Toast.LENGTH_SHORT).show();
        }
        else if(view.getId() == R.id.change_phone_btn){
            if(phone.length() != 10 || !TextUtils.isDigitsOnly(phone)){
                Toast.makeText(getApplicationContext(), "Invalid Phone Number!", Toast.LENGTH_SHORT).show();
            }
            else if(!phone.isEmpty()) {
                changePhoneNumber(phone);
                Toast.makeText(getApplicationContext(), "Account Phone Number Changed!", Toast.LENGTH_LONG).show();
                ((EditText) findViewById(R.id.change_phone)).setText("");
            }
            else Toast.makeText(getApplicationContext(), "Enter phone number to be changed!", Toast.LENGTH_SHORT).show();
        }
        else if(view.getId() == R.id.delete_account_btn){
            boolean canDelete = true; String errorMessage = "";
            ArrayList<Opportunity> opportunities = new ArrayList<>(getOpportunities());
            for(Opportunity opportunity : opportunities){
                if(Account.type.equals("Organizer")){
                    if(opportunity.getCreatorID().equals(Account.userID)){
                        canDelete = false; errorMessage = "Must delete all of your opportunities!";
                    }
                }
                else{
                    for(String uid : opportunity.getVolunteers().split(",")){
                        if (uid.equals(Account.userID)) {
                            canDelete = false;
                            errorMessage = "Must unregister all of your current opportunities!";
                            break;
                        }
                    }
                }
            }
            if(canDelete) {
                deleteAccount();
                Toast.makeText(getApplicationContext(), "Account Deleted!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), GoogleAuthentication.class));
            }
            else Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
