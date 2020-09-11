package com.example.videochatapp4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Main3Activity extends AppCompatActivity {

    Button notification;
    private FirebaseDatabase mDatabase ;
    DatabaseReference reff;
    Member member;
    private RequestQueue mRequestQue;
    private String URL="https://fcm.googleapis.com/fcm/send";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        notification= (Button) findViewById(R.id.notification);
        member=new Member();
        reff= FirebaseDatabase.getInstance().getReference().child("Member");

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendNotification();

            }
            });

    }


    private void sendNotification (){
        JSONObject mainObj= new JSONObject();

        try {
            mainObj.put("to", "/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","any title");
            notificationObj.put("body","any body");
            mainObj.put("notification",notificationObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL , mainObj , new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response){

                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                }
            }
            )
            {
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String , String > header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key= AIzaSyBuBTj-BYdIPyQJYTUchxinGqbKgW1vWP8");
                    return header;
                }
            };
            mRequestQue.add(request);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }
}


