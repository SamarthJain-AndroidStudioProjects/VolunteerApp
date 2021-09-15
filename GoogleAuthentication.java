package com.example.volunteer;

import static com.example.volunteer.VolunteerAppCloudDatabase.getUsers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.volunteer.Account.Account;
import com.example.volunteer.Account.ConfigureAccount;
import com.example.volunteer.Objects.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class GoogleAuthentication extends AppCompatActivity {
    private GoogleSignInClient client;
    private FirebaseAuth authorization;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_sign_in);
        create();
    }

    private void create(){
        Button signInButton = findViewById(R.id.sign_in_btn);
        if(!Account.botCheck){
            authorization = FirebaseAuth.getInstance();
            signInButton.setText(new String("Sign in with Google"));
            signInButton.setBackgroundColor(Color.BLUE);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            client = GoogleSignIn.getClient(this, gso);

            findViewById(R.id.sign_in_btn).setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Account.botCheck = true;
                    client.signOut();
                    Intent signInIntent = client.getSignInIntent();
                    startActivityForResult(signInIntent, 1);
                }
            });
        }
        else{
            Account.reset();
            signInButton.setText(new String("Next"));
            signInButton.setBackgroundColor(Color.BLACK);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) { configure(); }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException ignored) {}
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        authorization.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(getUsers().isEmpty()){ create(); }
                    else configure();
                } else {
                    Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void configure(){
        Account.userID = FirebaseAuth.getInstance().getUid();
        Account.email = Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getApplicationContext())).getEmail();

        boolean configured = false;
        for(User user : getUsers()){
            if(user.getUserID().equals(Account.userID)){
                Account.phone = user.getPhone(); Account.type = user.getType(); Account.name = user.getName();
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                configured = true; break;
            }
        }
        if(!configured) startActivity(new Intent(getApplicationContext(), ConfigureAccount.class));
    }
}

//TODO: ViewVolunteers NPE when volunteers list is empty