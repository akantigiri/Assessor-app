package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }


        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
TextView uo=(TextView) convertView.findViewById(R.id.timestamp);
        TextView uod=(TextView) convertView.findViewById(R.id.timestampname);

uod.setVisibility(View.GONE);
        FriendlyMessage message = getItem(position);
        String s = message.getName();String g=" ";
        int ry=MainActivity.act;
        if (s.equals(g)) {
            messageTextView.setVisibility(View.INVISIBLE);

            photoImageView.setVisibility(View.GONE);

        } else {
            boolean isPhoto = message.getPhotoUrl() != null;
            if (isPhoto) {
                messageTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.VISIBLE);
                Glide.with(photoImageView.getContext())
                        .load(message.getPhotoUrl())
                        .into(photoImageView);uo.setVisibility(View.INVISIBLE);uod.setVisibility(View.VISIBLE);
                        uod.setText(message.gettm());
            } else {
                messageTextView.setVisibility(View.VISIBLE);
                photoImageView.setVisibility(View.GONE);
                messageTextView.setText(message.getText());
            }
        }
        /*String v="";v=v+s.charAt(0);
if(v.equals("!")){authorTextView.setVisibility(View.INVISIBLE);}*/
        authorTextView.setText(message.getName());
if(ry==1){uo.setText("");}else{uo.setText(message.gettm());}

        return convertView;
    }


}
