package com.surakshasathi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ReportFragment extends Fragment {
    
    EditText compliantext;
    AppCompatButton submit;

    DatabaseReference databaseReference;
    SharedPreferenceClass sharedPreferenceClass;

    String currentDateTime="";
    AppCompatButton fetch;

    


    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate( R.layout.fragment_report, container, false );
         compliantext= view.findViewById( R.id.complaintText );
        submit = view.findViewById( R.id.submit);
        fetch = view.findViewById( R.id.fetch_complaints );
        databaseReference = FirebaseDatabase.getInstance().getReference("COMPAINTS");
        sharedPreferenceClass = new SharedPreferenceClass( getContext() );
        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle( "Do you Really want to Submit this complaint" )
                        .setPositiveButton( "Yes",null )
                        .setNegativeButton( "Cancel",null )
                        .create();


                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveBtn = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                String cmp = compliantext.getText().toString();
                                if(cmp.equals( "" )){
                                    Toast.makeText( getContext(), "Please enter something", Toast.LENGTH_SHORT ).show();
                                }
                                else{
                                    LocalDateTime now = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                        now = LocalDateTime.now();

                                        DateTimeFormatter formatter = null;
                                        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                        currentDateTime = now.format(formatter);

                                    }

                                    String name = sharedPreferenceClass.getValue_string( "name" );
                                    String phonenumber = sharedPreferenceClass.getValue_string( "phonenumber" );
                                    cmp = cmp + "\n"+"Name : "+name+"\n"+"PhoneNumber : "+phonenumber;



                                    String token = sharedPreferenceClass.getValue_string("token");

                                    databaseReference.child(token ).child( currentDateTime ).setValue( cmp );
                                    Toast.makeText( getContext(),"Complain Registered",Toast.LENGTH_SHORT ).show();
                                    compliantext.setText( "" );
                                }

                                dialog.dismiss();

                            }
                        });
                    }
                });



                dialog.show();


            }
        } );


        fetch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FetchComplaints.class);
                startActivity( intent );

            }
        } );



        return view;
    }



}