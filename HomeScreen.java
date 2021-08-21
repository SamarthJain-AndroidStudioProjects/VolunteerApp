package com.example.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        String displayName = "Welcome " + Account.name + "!";
        ((TextView) findViewById(R.id.display_name)).setText(displayName);

        if(Account.type.equals("Volunteer")){ findViewById(R.id.create_opportunity).setVisibility(View.INVISIBLE); }
        if(Account.type.equals("Organizer")){ findViewById(R.id.view_opportunities).setVisibility(View.INVISIBLE); }

        findViewById(R.id.my_opportunities).setOnClickListener(this);
        findViewById(R.id.create_opportunity).setOnClickListener(this);
        findViewById(R.id.view_opportunities).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.my_opportunities){
            startActivity(new Intent(getApplicationContext(), MyOpportunities.class));
        }
        else if(v.getId() == R.id.create_opportunity){
            startActivity(new Intent(getApplicationContext(), CreateOpportunity.class));
        }
        else if(v.getId() == R.id.view_opportunities){
            startActivity(new Intent(getApplicationContext(), ViewOpportunities.class));
        }
        else if(v.getId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), GoogleAuthentication.class));
        }
    }
}
