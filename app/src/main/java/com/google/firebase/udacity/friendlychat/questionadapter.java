package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class questionadapter extends ArrayAdapter<question> {
    public questionadapter(Context context, int resource, List<question> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.question_layout, parent, false);
        }
        TextView ques=(TextView) convertView.findViewById(R.id.ques);
        TextView opa=(TextView) convertView.findViewById(R.id.opat);
        TextView opb=(TextView) convertView.findViewById(R.id.opbt);
        TextView opc=(TextView) convertView.findViewById(R.id.opct);
        TextView opd=(TextView) convertView.findViewById(R.id.opdt);
        TextView crctop=(TextView) convertView.findViewById(R.id.opcrctt);
        CheckBox opac=(CheckBox) convertView.findViewById(R.id.opac);
        CheckBox opbc=(CheckBox) convertView.findViewById(R.id.opbc);
        CheckBox opcc=(CheckBox) convertView.findViewById(R.id.opcc);
       CheckBox opdc=(CheckBox) convertView.findViewById(R.id.opdc);
       ques.setVisibility(View.VISIBLE);
        opa.setVisibility(View.VISIBLE);opac.setVisibility(View.VISIBLE);
        opb.setVisibility(View.VISIBLE);opbc.setVisibility(View.VISIBLE);
        opc.setVisibility(View.VISIBLE);opcc.setVisibility(View.VISIBLE);
        opd.setVisibility(View.VISIBLE);opdc.setVisibility(View.VISIBLE);
        question obj= getItem(position);String e=obj.getque();
        if(e.equals("")){ques.setVisibility(View.GONE);}
        ques.setText(obj.getque());
        String a,b,c,d;
        a=obj.getopa();
        b=obj.getopb();
        c=obj.getopc();
        d=obj.getopd();
        if(a.equals("")){opa.setVisibility(View.GONE);opac.setVisibility(View.GONE);}
        if(b.equals("")){opb.setVisibility(View.GONE);opbc.setVisibility(View.GONE);}
        if(c.equals("")){opc.setVisibility(View.GONE);opcc.setVisibility(View.GONE);}
        if(d.equals("")){opd.setVisibility(View.GONE);opdc.setVisibility(View.GONE);}
        opa.setText(obj.getopa());
        opb.setText(obj.getopb());
        opc.setText(obj.getopc());
        opd.setText(obj.getopd());
        crctop.setText(" ");
        LinearLayout opaf=(LinearLayout) convertView.findViewById(R.id.opad);
        LinearLayout opbf=(LinearLayout) convertView.findViewById(R.id.opbd);
        LinearLayout opcf=(LinearLayout) convertView.findViewById(R.id.opcd);
        LinearLayout opdf=(LinearLayout) convertView.findViewById(R.id.opdd);
        final int vote = position;
        if(QuizActivity.ans[vote][0].equals("0")){opac.setChecked(false);}
        else{opac.setChecked(true);}
        if(QuizActivity.ans[vote][1].equals("0")){opbc.setChecked(false);}
        else{opbc.setChecked(true);}
        if(QuizActivity.ans[vote][2].equals("0")){opcc.setChecked(false);}
        else{opcc.setChecked(true);}
        if(QuizActivity.ans[vote][3].equals("0")){opdc.setChecked(false);}
        else{opdc.setChecked(true);}
        opac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Log.d("TAGE",""+vote+"a");
                if(QuizActivity.ans[vote][0].equals("0")){QuizActivity.ans[vote][0]="1";}
                else{QuizActivity.ans[vote][0]="0";}return ;

            }
        }); opbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAGE",""+vote+"b");
                if(QuizActivity.ans[vote][1].equals("0")){QuizActivity.ans[vote][1]="1";}
                else{QuizActivity.ans[vote][1]="0";}return ;

            }
        }); opcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Log.d("TAGE",""+vote+"c");
                if(QuizActivity.ans[vote][2].equals("0")){QuizActivity.ans[vote][2]="1";}
                else{QuizActivity.ans[vote][2]="0";}return ;

            }
        }); opdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Log.d("TAGE",""+vote+"d");
                if(QuizActivity.ans[vote][3].equals("0")){QuizActivity.ans[vote][3]="1";}
                else{QuizActivity.ans[vote][3]="0";}return ;

            }
        });

        return convertView;

    }
}
