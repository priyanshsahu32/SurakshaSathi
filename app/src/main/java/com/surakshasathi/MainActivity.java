

package com.surakshasathi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {





//    public static final MediaType JSON = MediaType.get("application/json");
//
//    OkHttpClient client = new OkHttpClient();


    private Toolbar toolbar;
//    private DrawerLayout drawerLayout;
//
//    private ActionBarDrawerToggle drawerToggle;
//
//    private NavigationView navigationView;











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

//        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout);
//
//        navigationView = (NavigationView) findViewById( R.id.navigationview);

        toolbar = findViewById( R.id.toolbar );



//        SharedPreferences user_todo = getSharedPreferences("user_todo", Activity.MODE_PRIVATE );
//        if(!user_todo.contains( "token" )){
//            showAlertDialog();
//
//        }




        setSupportActionBar( toolbar );
//        View hdview = navigationView.getHeaderView( 0 );

//        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                setDrawerClick(item.getItemId());
//                item.setChecked(true);
//                drawerLayout.closeDrawers();
//                return true;
//            }
//        } );

        initDrawer();


    }


    private void initDrawer() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.content,new Front());
        ft.commit();

//        drawerToggle = new ActionBarDrawerToggle( this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed( drawerView );
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened( drawerView );
//            }
//        };
//
//
//        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white ));
//
//        drawerLayout.addDrawerListener( drawerToggle );
    }
    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate( savedInstanceState);
//        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged( newConfig );
//        drawerToggle.onConfigurationChanged(  new Configuration());
    }


    private void setDrawerClick(int itemId){
//        if (itemId == R.id.action_report) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.content, new ReportFragment())
//                    .addToBackStack(null) // Add this line to enable back navigation
//                    .commit();
//        }
//        else if(itemId == R.id.review){
//            getSupportFragmentManager().beginTransaction()
//                    .replace( R.id.content,new Review() )
//                    .addToBackStack( null )
//                    .commit();
//        }
//
//        else if(itemId  == R.id.emergency){
//            getSupportFragmentManager().beginTransaction()
//                    .replace( R.id.content,new Emergency() )
//                    .addToBackStack( null )
//                    .commit();
//        }

    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected( item );
    }






}







