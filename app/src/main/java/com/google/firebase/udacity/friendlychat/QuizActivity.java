package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    String jo;
    String h;
    private static final String TAG = "MainActivity";
    public static String mu;
    public static FirebaseStorage mFirebaseStorage;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference;
    public static DatabaseReference nMessagesDatabaseReference;
    public static DatabaseReference mTimetdb;
    public static List<FriendlyMessage> friendlyMessages;
    public static questionadapter mquestionadapter;
    static int roh;
    public static String mUsername;
    private SharedPreferences sharedpreferences;
    private SharedPreferences shrdprfrncs;
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static int mSelectedItem;
    long mi, csk;
    int srh;
    SimpleDateFormat formatter;
    String huo;
    boolean l = true;
    private ListView mMessageListView;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditTextques;
    private EditText mMessageEditTextopA;
    private EditText mMessageEditTextopB;
    private EditText mMessageEditTextopC;
    private EditText mMessageEditTextopD;
    private EditText mMessageEditTextcrctop;
    private Button mQuiz;
    private long a, b, c, d;
    private EditText mDuration;
    private Button mSendButton;
    boolean y = false;
    private Button mChat;
    private Button mQuiza;
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
    String s1, s2, s3, s4, s5;
    int yo;
    String joe;
    String yoe;
    int dc;
    static int marks;
    static String[][] ans = new String[1000][4];
    static String pspk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.act = 1;
        yo = -1;
        super.onCreate(savedInstanceState);
        jo = NavActivity.y;
        if (jo.equals("0")) {
            jo = "messages";
        }
        joe = NavActivity.h;
        marks = 0;
        // msettimer.setEnabled(true);
        setContentView(R.layout.activity_quiz);
        sharedpreferences = getSharedPreferences("SCORE", Context.MODE_PRIVATE);
        shrdprfrncs = getSharedPreferences("tim", Context.MODE_PRIVATE);
        SharedPreferences.Editor esy = shrdprfrncs.edit();
        esy.remove("tim");
        esy.apply();
        esy.putInt("tim", -1);
        esy.apply();
        formatter = new SimpleDateFormat("h:mm a");
        huo = formatter.format(new Date());
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
        String nithin = SecondActivity.g + " " + NavActivity.x;
        setTitle(nithin);
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
        List<question> friendlyMessages = new ArrayList<>();
        mquestionadapter = new questionadapter(this, R.layout.question_layout, friendlyMessages);
        mMessageListView.setAdapter(mquestionadapter);
        mPhotoPickerButton.setVisibility(View.VISIBLE);
        mMessageEditTextques.setVisibility(View.VISIBLE);
        mSendButton.setVisibility(View.VISIBLE);
        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(QuizActivity.this, "Youre signed-in", Toast.LENGTH_SHORT).show();
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
                // TODO: Fire an intent to show an image picker
            }
        });
        mDuration.setVisibility(View.GONE);
        mAssgn.setVisibility(View.GONE);
        mQuiza.setVisibility(View.GONE);
        mMessageEditTextques.setVisibility(View.GONE);
        mMessageEditTextopA.setVisibility(View.GONE);
        mMessageEditTextopB.setVisibility(View.GONE);
        mMessageEditTextopC.setVisibility(View.GONE);
        mMessageEditTextopD.setVisibility(View.GONE);
        mMessageEditTextcrctop.setVisibility(View.GONE);
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 4; j++) {
                ans[i][j] = "0";
            }
        }

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
        mMessageEditTextques.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopC.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopD.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextcrctop.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mQuiz.setEnabled(true);
        final String[] gu = {""};
        Query qury = mMessagesDatabaseReference.orderByKey().limitToLast(1);
//Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
        qury.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                yo = shrdprfrncs.getInt("tim", -1);
                if (yo != -1) {
                    return;
                }
                String lo = "";
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    lo = esnapshot.child("que").getValue(String.class);
                    if (lo != null) {
                        Log.v("TAGE", lo);
                    }
                }
                if (lo == null) {
                    return;
                }
                gu[0] = lo;
                Log.d("TAGE", lo);

                //Toast.makeText(MainActivity.this,lo,Toast.LENGTH_SHORT).show();

                if (lo.length() < 5) {
                    return;
                }
                String g = "";
                g = g + lo.charAt(0);
                g = g + lo.charAt(1);
                g = g + lo.charAt(2);
                if (!g.equals("cmd")) {
                    return;
                }
                g = "";
                for (int i = 4; i < lo.length(); i++) {
                    g = g + lo.charAt(i);
                }
                yo = Integer.parseInt(g);
                Log.d("TAGE", "A" + yo);
                SharedPreferences.Editor esy = shrdprfrncs.edit();
                esy.remove("tim");
                esy.apply();
                esy.putInt("tim", yo);
                esy.apply();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!gu[0].equals("")) {
                    return;
                }
                if (l) {
                    return;
                }
                marks = 0;
                boolean dsc = true;
                srh = 0;
                String lo = "";
                String g;
                String goa = "";
                pspk = "";
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    lo = esnapshot.child("que").getValue(String.class);
                    Log.d("TAGE", lo + ans[srh][0] + ans[srh][1] + ans[srh][2] + ans[srh][3]);
                    if (lo.equals("")) {
                        srh++;
                        continue;
                    }
                    g = "";
                    if (lo.length() > 3) {
                        g = g + lo.charAt(0);
                        g = g + lo.charAt(1);
                        g = g + lo.charAt(2);
                        if (g.equals("cmd")) {
                            srh++;
                            continue;
                        }
                    }
                    goa = esnapshot.child("crctop").getValue(String.class);
                    lo = esnapshot.child("opa").getValue(String.class);
                    dc++;
                    pspk = pspk + dc + " " + goa + "\n";
                    Log.d("TAGE", lo + " " + goa);
                    assert goa != null;
                    if (goa.equals(lo)) {
                        if (ans[srh][0].equals("1") && ans[srh][1].equals("0") && ans[srh][2].equals("0") && ans[srh][3].equals("0")) {
                            srh++;
                            marks++;
                            continue;
                        } else {
                            srh++;
                            continue;
                        }
                    }
                    lo = esnapshot.child("opb").getValue(String.class);
                    if (goa.equals(lo)) {
                        if (ans[srh][1].equals("1") && ans[srh][0].equals("0") && ans[srh][2].equals("0") && ans[srh][3].equals("0")) {
                            srh++;
                            marks++;
                            continue;
                        } else {
                            srh++;
                            continue;
                        }
                    }
                    lo = esnapshot.child("opc").getValue(String.class);
                    if (goa.equals(lo)) {
                        if (ans[srh][2].equals("1") && ans[srh][0].equals("0") && ans[srh][1].equals("0") && ans[srh][3].equals("0")) {
                            srh++;
                            marks++;
                            continue;
                        } else {
                            srh++;
                            continue;
                        }
                    }
                    lo = esnapshot.child("opd").getValue(String.class);
                    if (goa.equals(lo)) {
                        if (ans[srh][3].equals("1") && ans[srh][0].equals("0") && ans[srh][2].equals("0") && ans[srh][1].equals("0")) {
                            srh++;
                            marks++;
                        } else {
                            srh++;
                        }
                    }
                }
                l = true;


            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        msettimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yo = shrdprfrncs.getInt("tim", 0);
                if (yo == -1) {
                    return;
                }
                msettimer.setEnabled(false);
                Log.d("TAGE", "" + yo);
                a = yo * 1000;
                Toast.makeText(QuizActivity.this, "Timer is set for " + (a / 1000) + " seconds", Toast.LENGTH_SHORT).show();

                new CountDownTimer(a, 1000) {

                    @Override
                    public void onTick(long a) {
                        b = a;
                        b = (a / 1000);
                        Log.v("TAGE", "UO" + b);
                        long hours = (b / 3600);
                        b -= (hours * 3600);

                        long minutes = (b / 60);
                        b -= (minutes * 60);

                        long seconds = b;
                        String g = "";


                        c = hours;
                        Log.v("TAGE", "" + c);
                        if (c < 10) {
                            g = g + "0" + c;
                        } else {
                            g = g + c;
                        }
                        c = minutes;
                        Log.v("TAGE", "" + c);
                        g = g + ":";
                        if (c < 10) {
                            g = g + "0" + c;
                        } else {
                            g = g + c;
                        }
                        g = g + ":";
                        c = seconds;
                        Log.v("TAGE", "" + c);
                        if (c < 10) {
                            g = g + "0" + c;
                        } else {
                            g = g + c;
                        }
                        mtime.setText(g);

                    }

                    public void onFinish() {
                        yo = shrdprfrncs.getInt("tim", -1);
                        if (yo == -1) {
                            return;
                        }
                        msettimer.setEnabled(true);
                        int score = sharedpreferences.getInt("SCORE", 0);
                        SharedPreferences.Editor ey = sharedpreferences.edit();
                        ey.remove("SCORE");
                        ey.apply();
                        ey.putInt("SCORE", -1);
                        y = false;
                        roh = score;
                        z = false;
                        yo = -1;
                        yo = -1;
                        l = false;
                        SharedPreferences.Editor esy = shrdprfrncs.edit();
                        esy.remove("tim");
                        esy.apply();
                        esy.putInt("tim", yo);
                        esy.apply();
                        question obj = new question("", "", "", "", "", "");
                        mMessagesDatabaseReference.push().setValue(obj);
                        Intent i = new Intent(QuizActivity.this, markactivity.class);
                        startActivity(i);


                    }
                }.start();

            }
        });
        mAssgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageEditTextques.setHint("Chat");
                mMessageEditTextopA.setVisibility(View.GONE);
                mMessageEditTextopB.setVisibility(View.GONE);
                mMessageEditTextopC.setVisibility(View.GONE);
                mMessageEditTextopD.setVisibility(View.GONE);
                mMessageEditTextcrctop.setVisibility(View.GONE);

                mtime.setVisibility(View.VISIBLE);
                mAssgn.setVisibility(View.GONE);
            }
        });
        mQuiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageEditTextques.setHint("Question");
                mMessageEditTextopA.setVisibility(View.VISIBLE);
                mMessageEditTextopB.setVisibility(View.VISIBLE);
                mMessageEditTextopC.setVisibility(View.VISIBLE);
                mMessageEditTextopD.setVisibility(View.VISIBLE);
                mMessageEditTextcrctop.setVisibility(View.VISIBLE);

                mtime.setVisibility(View.GONE);
                mAssgn.setVisibility(View.VISIBLE);
            }
        });

        mQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yo = shrdprfrncs.getInt("tim", -1);
                if (yo == -1) {
                    return;
                }
                msettimer.setEnabled(true);

                int score = sharedpreferences.getInt("SCORE", 0);
                SharedPreferences.Editor ey = sharedpreferences.edit();
                ey.remove("SCORE");
                ey.apply();
                ey.putInt("SCORE", -1);
                roh = score;
                y = false;
                z = false;
                String giri = "00:00:00";
                yo = -1;
                question obj = new question("", "", "", "", "", "");
                mMessagesDatabaseReference.push().setValue(obj);
                SharedPreferences.Editor esy = shrdprfrncs.edit();
                esy.remove("tim");
                esy.apply();
                esy.putInt("tim", yo);
                l = false;
                esy.apply();
                Intent i = new Intent(QuizActivity.this, markactivity.class);
                startActivity(i);

            }
        });


        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                String s = mMessageEditTextques.getText().toString();
                s1 = mMessageEditTextopA.getText().toString();
                s2 = mMessageEditTextopB.getText().toString();
                s3 = mMessageEditTextopC.getText().toString();
                s4 = mMessageEditTextopD.getText().toString();
                s5 = mMessageEditTextcrctop.getText().toString();
                if (s1.equals("") && s2.equals("") && s3.equals("") && s4.equals("") && s5.equals("")) {
                    question obj = new question("cmd " + s, s1, s2, s3, s4, s5);
                    mMessagesDatabaseReference.push().setValue(obj);
                } else {
                    l = true;
                    question obj = new question(s, s1, s2, s3, s4, s5);
                    mMessagesDatabaseReference.push().setValue(obj);
                }

                mMessageEditTextques.setText("");
                mMessageEditTextopA.setText("");
                mMessageEditTextopB.setText("");
                mMessageEditTextopC.setText("");
                mMessageEditTextopD.setText("");
                mMessageEditTextcrctop.setText("");
            }
        });
        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                question obj = mquestionadapter.getItem(position);

            }

        });


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
        mquestionadapter.clear();
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
                    question obj = snapshot.getValue(question.class);
                    mquestionadapter.add(obj);
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
        inflater.inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.setquiz:
                h = mUsername;
                h.trim();
                if (h.equals("Akanti Giri Nandan")) {
                    mMessageEditTextques.setVisibility(View.VISIBLE);
                    mMessageEditTextopA.setVisibility(View.VISIBLE);
                    mMessageEditTextopB.setVisibility(View.VISIBLE);
                    mMessageEditTextopC.setVisibility(View.VISIBLE);
                    mMessageEditTextopD.setVisibility(View.VISIBLE);
                    mMessageEditTextcrctop.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(QuizActivity.this, "Sorry! You are not allowed to do so.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.postquiz:
                h = mUsername;
                h.trim();
                if (h.equals("Akanti Giri Nandan")) {
                    mMessageEditTextques.setVisibility(View.GONE);
                    mMessageEditTextopA.setVisibility(View.GONE);
                    mMessageEditTextopB.setVisibility(View.GONE);
                    mMessageEditTextopC.setVisibility(View.GONE);
                    mMessageEditTextopD.setVisibility(View.GONE);
                    mMessageEditTextcrctop.setVisibility(View.GONE);
                } else {
                    Toast.makeText(QuizActivity.this, "Sorry! You are not allowed to do so.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.settime:
                h = mUsername;
                h.trim();
                if (h.equals("Akanti Giri Nandan")) {
                    mMessageEditTextques.setHint("Time");
                    mMessageEditTextques.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(QuizActivity.this, "Sorry! You are not allowed to do so.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.posttime:
                h = mUsername;
                h.trim();
                if (h.equals("Akanti Giri Nandan")) {
                    mMessageEditTextques.setHint("Question");
                    mMessageEditTextques.setVisibility(View.GONE);
                } else {
                    Toast.makeText(QuizActivity.this, "Sorry! You are not allowed to do so.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.deletequiz:
                h = mUsername;
                h.trim();
                if (h.equals("Akanti Giri Nandan")) {
                    mMessagesDatabaseReference.setValue(null);
                } else {
                    Toast.makeText(QuizActivity.this, "Sorry! You are not allowed to do so.", Toast.LENGTH_SHORT).show();
                }
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
        roh = score;
        y = false;
        z = false;
        String giri = "00:00:00";
        mtime.setText(giri);
        yo = -1;
        l = false;
        SharedPreferences.Editor esy = shrdprfrncs.edit();
        esy.remove("tim");
        esy.apply();
        esy.putInt("tim", yo);
        esy.apply(); /*Intent i=new Intent(QuizActivity.this,markactivity.class);
        startActivity(i);*/
        question obj = new question("", "", "", "", "", "");
        mMessagesDatabaseReference.push().setValue(obj);

        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
        mUsername = ANONYMOUS;
        mquestionadapter.clear();
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
        }
    }
}


