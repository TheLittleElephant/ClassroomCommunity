package com.hamoudi.j.classroomcommunity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by hamoudi on 16/02/2018.
 */

public class Friend {

    private int id;
    private String firstName;
    private String lastName;
    @SerializedName("connected")
    private boolean isConnected;
    private int lastScore;

    public Friend() {
    }

    public Friend(int id, String firstName, String lastName, boolean isConnected, int lastScore) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isConnected = isConnected;
        this.lastScore = lastScore;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public static Friend getFriendFromJson(String json) {
        Gson gson = new Gson();
        Friend friend = gson.fromJson(json, Friend.class);
        return friend;
    }

    public static List<Friend> getListOfFriendsFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Friend>>(){}.getType();
        List<Friend> friends = gson.fromJson(json, type);
        return friends;
    }

}
