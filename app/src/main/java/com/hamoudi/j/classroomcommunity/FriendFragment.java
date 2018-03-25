package com.hamoudi.j.classroomcommunity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class FriendFragment extends Fragment {

    private ProgressDialog friendsListDownloadProgressDialog;
    private List<Friend> friends;
    private ListView friendsList;

    public FriendFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Mes amis");
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        getFriends();
        friendsList = (ListView) view.findViewById(R.id.liste_amis);

        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Friend friend = (Friend) parent.getItemAtPosition(position);
            Intent intent = new Intent(getActivity(), QuizzActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("secondPlayer", friend.getFirstName());
            bundle.putString("secondPlayerPhoto", String.valueOf(friend.getFirstName().charAt(0)));
            intent.putExtras(bundle);
            startActivity(intent);

            }
        });

        return view;
    }

    public void pushAFriendRequest(Friend friend) {
        Ion.with(getContext())
                //paci.iut.1235
                .load("/putRequestFriend"
                        +"?key="+ MainActivity.QRCode
                        +"&my_id=" + MainActivity.ID
                        +"&friend_id=" + friend.getId())
                .asString()
                .withResponse() // Gestion des reponses
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(result == null) {

                        } else {
                            new TimerTask() {
                                @Override
                                public void run() {

                                }
                            }
                        }
                    }
                });
    }




    public static boolean checkConnection(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()
                || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        } else {
            return true;
        }
    }


    public void getFriends() {
        friendsListDownloadProgressDialog = new ProgressDialog(getActivity());
        friendsListDownloadProgressDialog.setMessage("Récupération de la liste des amis");
        friendsListDownloadProgressDialog.setIndeterminate(false);
        friendsListDownloadProgressDialog.setCancelable(true);
        friendsListDownloadProgressDialog.show();
        Ion.with(getActivity())
                .load(MainActivity.friendsUrl + "/" + MainActivity.QRCode)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {
                        friendsListDownloadProgressDialog.dismiss();
                        if(response == null) {
                            //Log.d(TAG, "No response from the server!!!");

                            Toast.makeText(getActivity(), "Le serveur ne répond pas", Toast.LENGTH_SHORT);
                        } else {

                            Log.d("TAG", "Http code: " + response.getHeaders().code());
                            Log.d("TAG", "Result: " + response.getResult());
                            friends = Friend.getListOfFriendsFromJson(response.getResult());
                            FriendAdapter friendAdapter = new FriendAdapter(getActivity(), R.layout.item_friend, friends);
                            friendsList.setAdapter(friendAdapter);
                        }
                    }
                });
        }

}
