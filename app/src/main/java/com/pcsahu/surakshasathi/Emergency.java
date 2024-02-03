package com.pcsahu.surakshasathi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Emergency extends Fragment {

    AppCompatButton call,location;
    String lat,log;
    public Emergency() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_emergency, container, false );
        call = view.findViewById( R.id.call );
        location = view.findViewById( R.id.location );


        location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmergencyLocation.class);
                startActivity( intent );
            }
        } );

        call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE )
                        == PackageManager.PERMISSION_GRANTED) {
                    // Create an intent to initiate a phone call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData( Uri.parse("tel:" + "000"));
                    startActivity(callIntent);
                } else {
                    // Request CALL_PHONE permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE}, 3);
                }

            }
        } );


        return view;
    }
}