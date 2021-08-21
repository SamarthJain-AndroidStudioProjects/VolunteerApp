package com.example.volunteer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConfigureAccount extends AppCompatActivity implements View.OnClickListener, Firebase {
    private Button organizer, volunteer;
    private String accountType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure_account);

        accountType = null;
        organizer = findViewById(R.id.organizer); organizer.setOnClickListener(this);
        volunteer = findViewById(R.id.volunteer); volunteer.setOnClickListener(this);
        findViewById(R.id.save_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.organizer){
            volunteer.setBackgroundColor(Color.BLACK);
            organizer.setBackgroundColor(Color.BLUE);
            accountType = organizer.getText().toString();
        }
        else if(v.getId() == R.id.volunteer){
            organizer.setBackgroundColor(Color.BLACK);
            volunteer.setBackgroundColor(Color.BLUE);
            accountType = volunteer.getText().toString();
        }
        else if(v.getId() == R.id.save_button){
            String phone = ((EditText) findViewById(R.id.phone_number)).getText().toString().trim().replace("-", "");

            if(accountType == null){
                Toast.makeText(getApplicationContext(), "Select Account Type!", Toast.LENGTH_SHORT).show();
            }
            else if(phone.length() != 10 || !TextUtils.isDigitsOnly(phone)){
                Toast.makeText(getApplicationContext(), "Invalid Phone Number!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Account Configured!", Toast.LENGTH_SHORT).show();
                Account.type = accountType; Account.phone = phone;
                addUserToFirebase();
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
            }
        }
    }
}