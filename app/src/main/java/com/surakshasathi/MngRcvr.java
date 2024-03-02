package com.surakshasathi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MngRcvr extends AppCompatActivity {

    SharedPreferenceClass sharedPreferenceClass;
    ArrayList<String> contacts;

    EditText phno;
    AppCompatButton add;
    ListView lv;
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mng_rcvr );
        contacts = new ArrayList<>();
        phno = findViewById( R.id.contact_et );
        add = findViewById( R.id.addcontact );
        lv = findViewById( R.id.listview );



//
        sharedPreferenceClass = new SharedPreferenceClass( this);
//
        contacts = sharedPreferenceClass.getArrayList( this );
//



        adapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1,contacts);
        lv.setAdapter( adapter );

        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_one = phno.getText().toString();
                if(!isvalid(new_one)){
                    Toast.makeText(MngRcvr.this, "Please enter a valid number", Toast.LENGTH_SHORT ).show();
                }
                else{
                    contacts.add( new_one );
                    adapter.notifyDataSetChanged();
                    sharedPreferenceClass.saveArrayList( MngRcvr.this,contacts );

                    phno.setText( "" );
                }
            }
        } );


        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDeleteConfirmationDialog( i );
            }
        } );









    }



    private void showDeleteConfirmationDialog(int idx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MngRcvr.this);
        builder.setTitle("Remover Receiver");
        builder.setMessage("Are you sure to remove this number?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contacts.remove( idx );
                adapter.notifyDataSetChanged();
                sharedPreferenceClass.saveArrayList(MngRcvr.this,contacts);

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    public static boolean isvalid(String str)
    {

        Pattern ptrn = Pattern.compile("(0/91)?[6-9][0-9]{9}");
//the matcher() method creates a matcher that will match the given input against this pattern
        Matcher match = ptrn.matcher(str);
//returns a boolean value
        return (match.find() && match.group().equals(str));
    }
}