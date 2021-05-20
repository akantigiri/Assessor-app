package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class markactivity extends AppCompatActivity {
    String jo;
    private static final String TAG = "MainActivity";
    public static String mu;
    public static FirebaseStorage mFirebaseStorage;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference;
    public static DatabaseReference mTimetdb;
    public static List<FriendlyMessage> friendlyMessages;
    public static MessageAdapter mMessageAdapter;
    SimpleDateFormat formatter;
    public static String mUsername;
    private SharedPreferences sharedpreferences;
    private SharedPreferences shrdprfrncs;
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static int mSelectedItem;
    long mi, csk;
    static String fy;
    private ListView mMessageListView;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditTextques;
    private EditText mMessageEditTextopA;
    private EditText mMessageEditTextopB;
    private EditText mMessageEditTextopC;
    private EditText mMessageEditTextopD;
    private EditText mMessageEditTextcrctop;
    private TextView Timestamp;
    private Button mQuiz;
    private long a, b, c, d;
    private EditText mDuration;
    private Button mSendButton;
    boolean y = false;
    private Button mChat;
    private Button mQuiza;
    String huo;
    private Button mAssgn;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;
    public static final int RC_PHOTO_PICKER = 2;
    private StorageReference mChatPhotosStorageReference;
    private Button msettimer;
    private TextView mtime;
    private boolean z = false;
    String s1, s2, s3, s4;
    int yo;
    static int act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        act=0;
        yo = -1;
        super.onCreate(savedInstanceState);
        jo = SecondActivity.o;
        // msettimer.setEnabled(true);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences("SCORE", Context.MODE_PRIVATE);
        shrdprfrncs = getSharedPreferences("tim", Context.MODE_PRIVATE);
        SharedPreferences.Editor esy = shrdprfrncs.edit();

        esy.remove("tim");
        esy.apply();
        esy.putInt("tim", -1);
        esy.apply();
        FirebaseApp.initializeApp(this);
        FirebaseDatabase mFirebaseDatabase;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("chat_photos");
        mDuration = (EditText) findViewById(R.id.duration);
        mQuiza = (Button) findViewById(R.id.Quiz);
        mAssgn = (Button) findViewById(R.id.Assgn);
        String u = mDuration.getText().toString();

        formatter = new SimpleDateFormat("h:mm a");
        huo=formatter.format(new Date());


        if (u.length() > 0) {
            y = true;
        }
        if (y) {
            c = Integer.parseInt(mDuration.getText().toString());
            if (c > 0) {
                a = Integer.parseInt(mDuration.getText().toString());
                a = a * 1000;
            }
        }
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child(jo);
        mMessagesDatabaseReference = mFirebaseDatabase.getReference("").child(jo);
        mTimetdb = mFirebaseDatabase.getReference().child("timet");
        mTimetdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lo = "";
                for (DataSnapshot locationSnapshot : snapshot.getChildren()) {
                    lo = locationSnapshot.getValue().toString().trim();
                }
                Log.d("TAGE", "AB" + lo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAGE", "ABCD");

            }
        });
        mUsername = ANONYMOUS;
        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditTextques = (EditText) findViewById(R.id.messageEditTextques);
        mMessageEditTextopA = (EditText) findViewById(R.id.messageEditTextopA);
        mMessageEditTextopB = (EditText) findViewById(R.id.messageEditTextopB);
        mMessageEditTextopC = (EditText) findViewById(R.id.messageEditTextopC);
        mMessageEditTextopD = (EditText) findViewById(R.id.messageEditTextopD);
        mMessageEditTextcrctop = (EditText) findViewById(R.id.messageEditTextcrctop);
        mSendButton = (Button) findViewById(R.id.sendButton);
        msettimer = (Button) findViewById(R.id.settimer);
        mQuiz = (Button) findViewById(R.id.finishQuiz);
        mtime = (TextView) findViewById(R.id.time);
        // Initialize message ListView and its adapter
        y = false;
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
            }
        });

        mMessageEditTextopA.setVisibility(View.GONE);
        mMessageEditTextopB.setVisibility(View.GONE);
        mMessageEditTextopC.setVisibility(View.GONE);
        mMessageEditTextopD.setVisibility(View.GONE);
        mMessageEditTextcrctop.setVisibility(View.GONE);
        mDuration.setVisibility(View.GONE);
        mAssgn.setVisibility(View.GONE);
        msettimer.setVisibility(View.GONE);
        mQuiz.setVisibility(View.GONE);
        mQuiza.setVisibility(View.GONE);
        mtime.setVisibility(View.GONE);
mPhotoPickerButton.setVisibility(View.GONE);
mMessageEditTextques.setVisibility(View.GONE);
mSendButton.setVisibility(View.GONE);
        // Enable Send button when there's text to send
        mMessageEditTextques.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        String nithin="Quiz"+" "+"Results";
        setTitle(nithin);

        mMessageEditTextques.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopC.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopD.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextcrctop.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mQuiz.setEnabled(true); formatter = new SimpleDateFormat("h:mm a");
        huo=formatter.format(new Date());
        mUsername=QuizActivity.mUsername;mUsername.trim();
        if(mUsername.equals("Akanti Giri Nandan")){
            String ufo = "" + QuizActivity.pspk;
            FriendlyMessage obj=new FriendlyMessage(ufo,mUsername,null,huo);
            mMessagesDatabaseReference.push().setValue(obj);
         ufo = "" + QuizActivity.marks;
             obj=new FriendlyMessage(ufo,mUsername,null,huo);
            mMessagesDatabaseReference.push().setValue(obj);

        }
        else {
            String ufo = "" + QuizActivity.marks;
            FriendlyMessage obj=new FriendlyMessage(ufo,mUsername,null,huo);
            mMessagesDatabaseReference.push().setValue(obj);
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(markactivity.this, "Youre signed-in", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getDisplayName());

                } else {
                    onSignedOutCleanup();
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build())).build(), RC_SIGN_IN);
                }
            }
        };
        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });
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
            mu = mUsername;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        yo = shrdprfrncs.getInt("tim", 0);
        if (yo == -1) {
            return;
        }
        msettimer.setEnabled(true);

        int score = sharedpreferences.getInt("SCORE", 0);
        SharedPreferences.Editor ey = sharedpreferences.edit();
        ey.remove("SCORE");
        ey.apply();
        formatter = new SimpleDateFormat("h:mm a");
        huo=formatter.format(new Date());
        FriendlyMessage friendlyMessage = new FriendlyMessage("" + score, mUsername, null,huo);
        mMessagesDatabaseReference.push().setValue(friendlyMessage);
        y = false;
        z = false;
        String giri = "00:00:00";
        mtime.setText(giri);
        yo = -1;
        SharedPreferences.Editor esy = shrdprfrncs.edit();
        esy.remove("tim");
        esy.apply();
        esy.putInt("tim", yo);
        esy.apply();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        } else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final StorageReference ref = mChatPhotosStorageReference.child(uri.getLastPathSegment());
            ref.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        formatter = new SimpleDateFormat("h:mm a");
                        huo=formatter.format(new Date());
                        FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, downloadUri.toString(),huo);
                        mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    } else {
                        Toast.makeText(markactivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


