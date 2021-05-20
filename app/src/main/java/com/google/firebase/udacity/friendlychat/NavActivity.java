package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.firebase.ui.auth.AuthUI;

public class NavActivity extends Activity {
    LinearLayout imgButton;
    static String h;
    static String z;
    static String y;
    static String x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        imgButton = (LinearLayout) findViewById(R.id.a);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { h = SecondActivity.hy;x="Quiz";
                if (h.equals("messages")) {
                    y = "CSN_254E";z="CSN_254F";
                } else {
                    z=SecondActivity.hy+"F";
                    y = SecondActivity.hy + "E";
                }
                Intent i=new Intent(NavActivity.this,QuizActivity.class);
                startActivity(i);

            }
        });
        imgButton = (LinearLayout) findViewById(R.id.b);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {x="Polls";
                Intent i=new Intent(NavActivity.this,myapplication.class);
                startActivity(i);

            }
        });
        imgButton = (LinearLayout) findViewById(R.id.c);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {h=SecondActivity.hy;x="Chat";
                Intent i=new Intent(NavActivity.this,MainActivity.class);
                startActivity(i);

            }
        });
        imgButton = (LinearLayout) findViewById(R.id.d);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h = SecondActivity.hy;
                if (h.equals("messages")) {
                    z= "CSN_254D";
                } else {
                    z = SecondActivity.hy + "D";
                }
                h=z;x="Assignment";

                Intent i = new Intent(NavActivity.this, MainActivity.class);
                startActivity(i);
            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
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
}