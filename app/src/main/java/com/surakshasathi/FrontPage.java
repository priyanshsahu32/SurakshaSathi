package com.surakshasathi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FrontPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_front_page );

//        chatbot.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent(FrontPage.this, MainActivity.class) );
//            }
//        } );
//        emergency.setOnClickListener( new View.OnClickListener() {
//
//
//
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.containerid,new Emergency())
//                        .addToBackStack( null )
//                        .commit();
//            }
//        } );
    }
}