package com.example.android.bluetoothchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WifiActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        text = (TextView) findViewById(R.id.commande);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://hasnimedbilel-cf7f6.firebaseio.com/");
    }


    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference myChild = databaseReference.getRef().child("commande");
        myChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ch = dataSnapshot.getValue(String.class);
                text.setText(ch);
                BluetoothChatFragment.sendMessage(ch);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
