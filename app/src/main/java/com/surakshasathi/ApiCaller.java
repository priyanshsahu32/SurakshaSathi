package com.surakshasathi;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ApiCaller {

    private String stringURLEndPoint="https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyDQz5eOeIpUoP5QzO00tavD17bW21G1yKQ";

    private Context context;

    public ApiCaller(Context context) {
        this.context = context;
    }

    public CompletableFuture<String> asyncApiCall(String question) {
        CompletableFuture<String> future = new CompletableFuture<>();

        JSONObject jsonObject = new JSONObject();
        try {
            // Construct your JSON request here...
            // (Same as your original code)


            JSONArray contentsArray = new JSONArray();
            JSONObject contentsObject = new JSONObject();

            JSONArray partsArray = new JSONArray();
            JSONObject partsObject = new JSONObject();
            partsObject.put("text", question);
            partsArray.put(partsObject);

            contentsObject.put("parts", partsArray);
            contentsArray.put(contentsObject);

            jsonObject.put("contents", contentsArray);

        } catch (JSONException e) {
            future.completeExceptionally(e);
            return future;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stringURLEndPoint, jsonObject,
                response -> {
                    try {
                        // Handle the API response and complete the CompletableFuture
                        String stringText = response.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
                        future.complete(stringText);
                    } catch (JSONException e) {
                        future.completeExceptionally(e);
                    }
                },
                error -> {
                    error.printStackTrace();
                    future.completeExceptionally(error);
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapHeader = new HashMap<>();
                // Add your headers here...
                mapHeader.put("Content-Type", "application/json");
                return mapHeader;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        int intTimeoutPeriod = 60000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(intTimeoutPeriod, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);

        // Add the request to the Volley queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

        return future;
    }
}
