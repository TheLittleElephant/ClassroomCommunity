package com.hamoudi.j.classroomcommunity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;

    public static String friendsUrl = "http://192.168.0.27:8000/getFriends";
    public static String checkAttendingUrl = "http://192.168.0.27:8000/checkAttending";
    public static String questionsUrl = "http://192.168.0.27:8000/getQuestions";
    public static String QRCode;
    public static String ID = "1";
   /* public static String friendsUrl = "http://vs-wamp/Dep_info/sil/sili/SILI_classroom_server/getFriends.php";
    public static String checkAttendingUrl = "http://vs-wamp/Dep_info/sil/sili/SILI_classroom_server/checkAttending.php";
    public static String QRCode;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CAMERA) !=  PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},99);
        } else { /* permission autorisée déjà...*/

        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.ouvrir, R.string.fermer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().performIdentifierAction(R.id.nav_scancode, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_friends:
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new FriendFragment()).commit();
                break;
            case R.id.nav_scancode:
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new ScanCodeFragment()).commit();
                break;
            case R.id.nav_settings:
                //startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.logout_confirm)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==99 && grantResults[0]==PackageManager.PERMISSION_GRANTED){ /* permission autorisée ... */
        }
    }

    private boolean checkCameraHardware(Context ctx) {
        if(ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            /*  Cet appareil possède une caméra ...*/
            return true; }
        else { /*Cet appareil ne possède pas une caméra !!! ...*/
            return false;
        }
    }

}
