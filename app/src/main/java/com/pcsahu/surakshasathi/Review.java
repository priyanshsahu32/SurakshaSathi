package com.pcsahu.surakshasathi;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Review extends Fragment {

    SharedPreferenceClass sharedPreferenceClass;
    DatabaseReference databaseReference;
    EditText reviewtext;
    AppCompatButton reviewsubmit;
    String currentDateTime,txt;



    public Review() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate( R.layout.fragment_review, container, false );

        sharedPreferenceClass = new SharedPreferenceClass( getContext() );
        databaseReference = FirebaseDatabase.getInstance().getReference("REVIEW");
        reviewsubmit = view.findViewById( R.id.reviewsub );
        reviewtext = view.findViewById(R.id.reviewtext );

        reviewsubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = reviewtext.getText().toString();
                if(!txt.equals( "" )){
                    String token = sharedPreferenceClass.getValue_string("token");


                    LocalDateTime now = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        now = LocalDateTime.now();

                        DateTimeFormatter formatter = null;
                        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        currentDateTime = now.format(formatter);

                    }
                    databaseReference.child( token ).child( currentDateTime ).setValue( txt );
                    Toast.makeText(getContext(),"Thankyou for submitting you View",Toast.LENGTH_SHORT).show();


                }
            }
        } );

        return view;
    }
}