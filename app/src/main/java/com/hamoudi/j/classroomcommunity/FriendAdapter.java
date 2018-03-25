package com.hamoudi.j.classroomcommunity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamoudi.j.classroomcommunity.Friend;

import java.util.List;

/**
 * Created by Jonathan on 19/02/2018.
 */

public class FriendAdapter extends ArrayAdapter<Friend> {
    Activity activity;
    int itemLayoutResourceId;
    List<Friend> friends;

    public FriendAdapter( Activity activity, int profilePhotoResourceId, List<Friend> friends) {
        super(activity, profilePhotoResourceId, friends);
        this.activity = activity;
        this.itemLayoutResourceId = profilePhotoResourceId;
        this.friends = friends;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = convertView;
        if(convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            layout = inflater.inflate(itemLayoutResourceId, parent, false);
        }

        //ImageView imageView = (ImageView) layout.findViewById(R.id.ivProfilePhoto);
        TextView profilePhoto = (TextView) layout.findViewById(R.id.tvProfilePhoto);
        TextView friendName = (TextView) layout.findViewById(R.id.tvPlayerName);
        TextView isPresent = (TextView) layout.findViewById(R.id.tvIsPresent);

        if(friends.get(position).isConnected()) {
            isPresent.setText("Disponible");
            isPresent.setTextColor(layout.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            isPresent.setText("Non connecté");
            isPresent.setTextColor(layout.getResources().getColor(android.R.color.holo_red_light));
            layout.setAlpha((float) 0.35);
        }

        //imageView.setImageResource(friends.get(position).getPhotoResourceId());
        profilePhoto.setText(String.valueOf(friends.get(position).getFirstName().toUpperCase().charAt(0)));
        friendName.setText(friends.get(position).getFirstName());

        return layout;
    }
}
