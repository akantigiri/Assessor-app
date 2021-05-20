package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView course1,course2,name,course3;
static String hy="0";
    static String x="0";
    static String y="0";static String z="0";
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static String mUsername;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static MessageAdapter mMessageAdapter;
    public static final int RC_SIGN_IN = 1;

    public static DatabaseReference mMessagesDatabaseReference;

static String g="";
static String o="";
    private int requestCode;
    private int resultCode;
    @Nullable
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        FirebaseApp.initializeApp(this);
        FirebaseDatabase mFirebaseDatabase;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");
        mMessagesDatabaseReference = mFirebaseDatabase.getReference("").child("messages");
        mUsername = ANONYMOUS;
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        ArrayList<strng> a=new ArrayList<strng>();
        a.add(new strng("CSN-254","Software Engineering"));
        a.add(new strng("CSN-212"," Design and Analysis of Algorithms"));
        a.add(new strng("CSN-252"," System Software"));
        a.add(new strng("MTN-105"," Electronic and Electrical Materials"));
        a.add(new strng("ECN-252"," Digital Electronic Circuits Labaratory "));
        a.add(new strng("CSN-351"," Database Management Systems"));
        ListView l=(ListView) findViewById(R.id.listay);
        strngadapter b=new strngadapter(this,a);
        l.setAdapter(b);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             if(position==3){hy="MTN_105";x="MTN_105A";y="MTN_105B";z="MTN_105C";o="MTN_105G";
                 Intent i=new Intent(SecondActivity.this,NavActivity.class);
                 startActivity(i);g="MTN_105";
                }
             else if(position==1){hy="CSN_212";x="we";y="ew";z="es";o="CSN_212G";
                 Intent i=new Intent(SecondActivity.this,NavActivity.class);
                 startActivity(i);g="CSN_212";

             }
             else if(position==2){hy="CSN_252";x="CSN_252A";y="CSN_252B";z="CSN_252C";o="CSN_252G";
                 Intent i=new Intent(SecondActivity.this,NavActivity.class);
                 startActivity(i);g="CSN_252";
                }
             else if(position==4){ hy="ECN_252";x="ECN_252A";y="ECN_252B";z="ECN_252C";o="ECN_252G";
                 Intent i=new Intent(SecondActivity.this,NavActivity.class);
                 startActivity(i);g="ECN_252";

             }
             else if(position==5){hy="CSN_351";x="CSN_351A";y="CSN_351B";z="CSN_351C";o="CSN_351G";
                 Intent i=new Intent(SecondActivity.this,NavActivity.class);
                 startActivity(i);g="CSN_351";}
                 else {
hy="messages";x="polls";y="resp";z="pok";o="CSN_254G";
                 Intent i=new Intent(SecondActivity.this,NavActivity.class);
                 startActivity(i);g="CSN_254";}

            }
        });
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(SecondActivity.this, "Youre signed-in", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getDisplayName());

                } else {
                    onSignedOutCleanup();
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build())).build(), RC_SIGN_IN);
                }
            }
        };








    }
    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        mMessageAdapter.clear();
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
    private void onSignedInInitialize(String displayName) {
        if (mChildEventListener == null) {
            mUsername = displayName;
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    FriendlyMessage obj = snapshot.getValue(FriendlyMessage.class);
                    mMessageAdapter.add(obj);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sec_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
        mUsername = ANONYMOUS;
        mMessageAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

}