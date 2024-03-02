package com.surakshasathi;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.checkerframework.common.util.report.qual.ReportReadWrite;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Front extends Fragment {

    SharedPreferenceClass sharedPreferenceClass;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 10;

    ImageButton chatbot,complain,childright,helpline,emergency,review,quiz,parental;

    public Front() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  =  inflater.inflate( R.layout.fragment_front, container, false );


        sharedPreferenceClass = new SharedPreferenceClass( getContext() );


        SharedPreferences user_todo = getContext().getSharedPreferences("user_todo", Activity.MODE_PRIVATE );
        if(!user_todo.contains( "token" )){

            showAlertDialog();


        }

        chatbot = view.findViewById( R.id.chatimage );
        complain = view.findViewById( R.id.reportimage );
        childright  =view.findViewById( R.id.childrightimage );
        helpline = view.findViewById( R.id.helpineimage );
        emergency = view.findViewById( R.id.emergencyImage );
        review = view.findViewById( R.id.REVIEWIMAGE );
        quiz = view.findViewById( R.id.quizimage );
        parental =view.findViewById( R.id.parental );




        chatbot.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content,new ChildRight() )
                        .addToBackStack( null )
                        .commit();

            }
        } );
        complain.setOnClickListener( new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace( R.id.content,new Emergency() )
                        .addToBackStack( null )
                        .commit();

            }
        } );

        childright.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace( R.id.content,new ParentalInvolvement() )
                        .addToBackStack( null )
                        .commit();
            }
        } );

        helpline.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace( R.id.content,new ReportFragment() )
                        .addToBackStack( null )
                        .commit();
            }
        } );

        emergency.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace( R.id.content,new HomeFragment() )
                        .addToBackStack( null )
                        .commit();
            }
        } );

        review.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace( R.id.content,new Helpline() )
                        .addToBackStack( null )
                        .commit();
            }
        } );
        quiz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity( new Intent(getActivity(), QuizActivity.class) );

            }
        } );
        parental.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace( R.id.content,new Review() )
                        .addToBackStack( null )
                        .commit();
            }
        } );




        return view;
    }


    static String makeAkey() {
        StringBuilder str = new StringBuilder();
        int i = 50;
        Random random = new Random();
        while (i > 0) {

            char ch = (char) random.nextInt(256);
            char c = (ch<256)?(char)(ch+42):(char)((ch%256)+42);
            while (!Character.isLetterOrDigit(c+42) || c == '.' || c == '#' || c == '$' || c == '[' || c == ']' || c > 127||c=='/') {
                ch = (char) random.nextInt(256);
                c = (ch<256)?(char)(ch+42):(char)((ch%256)+42);
            }
            str.append(ch);
            i--;
        }
        return str.toString();
    }


    static String encrypt(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int ascii = (int) c;
            ascii += 42;
            if (ascii > 256) {
                ascii = ascii % 256;
            }
            sb.append((char) ascii);
        }
        return sb.toString();
    }



    private void showAlertDialog() {



        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate( R.layout.custom_dialog_layout,null  );
        final EditText title_field = alertLayout.findViewById(R.id.title);

        final EditText description_field = alertLayout.findViewById(R.id.description);


        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView( alertLayout )
                .setTitle( "Welcome to Suraksha Sathi App" )
                .setPositiveButton( "Submit",null )
                .setCancelable( false )
                .setNegativeButton( "Cancel",null )
                .create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String titlestr = title_field.getText().toString();
                        String desc = description_field.getText().toString();
                        boolean isvalid = isValidMobileNo( desc );

                        if (!TextUtils.isEmpty( desc ) && !TextUtils.isEmpty(titlestr) && isvalid) {
                            SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass( getContext());
                            sharedPreferenceClass.setValue_string( "name",titlestr);
                            sharedPreferenceClass.setValue_string( "phonenumber",desc );

                            String key  = makeAkey();
                            key = encrypt( key );


                            sharedPreferenceClass.setValue_string( "token",key );
                            Toast.makeText( getContext(),"Welcome To Suraksha Sathi",Toast.LENGTH_SHORT ).show();

                            dialog.dismiss();
                        } else {
                            if(TextUtils.isEmpty( desc ) || TextUtils.isEmpty(titlestr)){
                                Toast.makeText(getContext(), "Please Fill entries", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText( getContext(),"Please enter a valid mobile number",Toast.LENGTH_SHORT ).show();
                            }
                        }
                    }
                });

                Button negativeBtn = ((AlertDialog)dialog).getButton( AlertDialog.BUTTON_NEGATIVE );

                negativeBtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText( getContext(),"User Details is neccessary to enter for new user",Toast.LENGTH_SHORT).show();
                        sharedPreferenceClass.clear();
                        dialog.dismiss();

                        showAlertDialog();


                    }
                } );
            }
        });



        dialog.show();






    }


    public static boolean isValidMobileNo(String str)
    {

        Pattern ptrn = Pattern.compile("(0/91)?[6-9][0-9]{9}");
//the matcher() method creates a matcher that will match the given input against this pattern
        Matcher match = ptrn.matcher(str);
//returns a boolean value
        return (match.find() && match.group().equals(str));
    }

}