package com.example.volunteer;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public interface Firebase {

    default void add(String email, String phone, String type) {
        User user = new User(email, phone, type);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Account.userID);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(user);
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    default void retrieveData(FirebaseCallback firebaseCallback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<User> users = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String value = child.getValue().toString()
                                .replace("{phone=","")
                                .replace(" type=","")
                                .replace(" email=","")
                                .replace("}","");
                        users.add(new User(value.split(",")[2], value.split(",")[0], value.split(",")[1]));
                    }
                    firebaseCallback.onCallBack(users);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    interface FirebaseCallback {
        void onCallBack(ArrayList<User> list);
    }
}

