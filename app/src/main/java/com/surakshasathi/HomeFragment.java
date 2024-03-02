package com.surakshasathi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    EditText message;
    ImageButton send;
    List<Message> messageList;
    Adapter messageAdapter;
    String msg,sendBy;



//    private String stringURLEndPoint = "https://api.openai.com/v1/completions";
    private String stringURLEndPoint="https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyDQ-a_lht3tXSlSB3MMlfnUCX8lrNJu99A";
    private String apikey = "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";
    private String resp = "";



    public HomeFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate( R.layout.fragment_home, container, false );
        recyclerView =view.findViewById( R.id.recyclerview );
        message  = view.findViewById( R.id.message_edit_text );
        send =view.findViewById(R.id.send_btn );
        messageList = new ArrayList<>();
        messageAdapter = new Adapter( messageList );
        recyclerView.setAdapter( messageAdapter );










        LinearLayoutManager llm = new LinearLayoutManager( getContext() );
        llm.setStackFromEnd( true );
        recyclerView.setLayoutManager( llm );
        send.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question = message.getText().toString();
                message.setText( "" );
//
                if(!question.equals( "")){
                    sendBy = Message.send_by_me;
                    addtochat(question,sendBy);
//                new ApiCallTask().execute(question);
//                    apicall(question);
//                    new Handler().postDelayed( new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                        }
//                    } ,10000);




                    ApiCaller apiCaller = new ApiCaller(getContext());
                    apiCaller.asyncApiCall(question)
                            .thenAccept(result -> {

                                resp = result;

                                if(!resp.equals( "" )){
                                    sendBy = Message.send_by_bot;
                                    addtochat( resp,sendBy );
                                    resp = "";
                                }
                                else{
                                    Toast.makeText( getContext(),"Something went wrong",Toast.LENGTH_SHORT ).show();
                                }



                            })
                            .exceptionally(ex -> {
                                // Handle exceptions here
                                ex.printStackTrace();
                                return null;
                            });





                }
                else{
                    Toast.makeText( getContext(),"please type something" , Toast.LENGTH_SHORT).show();
                }
            }
        } );
        return view;

    }


//    private void apicall(String question) {
//        JSONObject jsonObject = new JSONObject();
//        try {
////            jsonObject.put( "model","gpt-3.5-turbo-instruct" );
//////                JSONArray jsonArrayMessage = new JSONArray();
//////                JSONObject jsonObjectMeassage = new JSONObject();
////
//////                jsonObjectMeassage.put("role","user");
////            jsonObject.put("prompt",question);
//////                jsonArrayMessage.put(jsonObjectMeassage);
//////                jsonObject.put( "messages",jsonArrayMessage );
//
//            JSONArray contentsArray = new JSONArray();
//            JSONObject contentsObject = new JSONObject();
//
//            JSONArray partsArray = new JSONArray();
//            JSONObject partsObject = new JSONObject();
//            partsObject.put("text", question);
//            partsArray.put(partsObject);
//
//            contentsObject.put("parts", partsArray);
//            contentsArray.put(contentsObject);
//
//            jsonObject.put("contents", contentsArray);
//
//
//
//
//
//
//
//
//
//
//        } catch (JSONException e) {
//            throw new RuntimeException( e );
//        }
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, stringURLEndPoint, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                String stringText = null;
//                try {
////                    stringText = response.getJSONArray( "choices" )
////                            .getJSONObject( 0 )
////                            .getString( "text" );
//
//
//
//                        stringText = response.getJSONArray( "candidates" ).getJSONObject( 0).getJSONObject( "content" ).getJSONArray( "parts" ).getJSONObject( 0 ).getString( "text" );
//                } catch (JSONException e) {
//                    throw new RuntimeException( e );
//                }
//
//                resp = resp + stringText;
////                resp = resp.trim();
//
//
//
//            }
//
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Toast.makeText( getContext(),"ERROR",Toast.LENGTH_SHORT ).show();
//
//            }
//        } ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> mapHeader = new HashMap<>();
////                mapHeader.put( "Authorization","Bearer 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" );
//                mapHeader.put("Content-Type","application/json");
//
//                return mapHeader;
//            }
//
//            @Override
//            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                return super.parseNetworkResponse( response );
//            }
//        };
//
//        int intTimeoutPeriod = 60000;
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(intTimeoutPeriod,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        jsonObjectRequest.setRetryPolicy( retryPolicy );
//
//        Volley.newRequestQueue( getContext()).add(jsonObjectRequest);
//
//    }



    private void addtochat(String que, String sendBy) {
        messageList.add( new Message(que,sendBy) );
        messageAdapter.notifyItemInserted(messageList.size() - 1);

        recyclerView.smoothScrollToPosition( messageAdapter.getItemCount() );
        return;
    }







}









