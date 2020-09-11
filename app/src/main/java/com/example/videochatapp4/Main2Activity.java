package com.example.videochatapp4;
// doctor
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;
import androidx.annotation.NonNull;
import android.Manifest;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.opengl.GLSurfaceView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;





import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements  Session.SessionListener, PublisherKit.PublisherListener{
    private static String API_KEY = "46536782";
    //secretKey--->1106a87c45553c87791efec627dcd1ef4ea5fcd0
    //session_id="2_MX40NjUzNjc4Mn5-MTU4NDA3Nzc1MjIyNn4vVVVlRjkydmRzZ1Q1VFB4Y3kyRk0vOHF-fg"
    // token="T1==cGFydG5lcl9pZD00NjUzNjc4MiZzaWc9MDU2YTE5NWQxMmE5NDRmMzJmOTZkMThiYTZhYWJkMDZmZTdiMTkxMjpzZXNzaW9uX2lkPTJfTVg0ME5qVXpOamM0TW41LU1UVTROREEzTnpjMU1qSXlObjR2VlZWbFJqa3lkbVJ6WjFRMVZGQjRZM2t5Umswdk9IRi1mZyZjcmVhdGVfdGltZT0xNTg0MDc3NzYzJm5vbmNlPTAuNDY5ODM2ODAwODk5MjIyJnJvbGU9cHVibGlzaGVyJmV4cGlyZV90aW1lPTE1ODQxNjQxMzgmaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0="
    private static String SESSION_ID = " ";
    private static String TOKEN = " ";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private Session mSession;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    DatabaseReference reff;
    DatabaseReference reff2;
    session sessaion;
    long maxid=0;
    long count=0;
    private RequestQueue mRequestQue;
    private String URL="https://fcm.googleapis.com/fcm/send";

    private void sendNotification (){
        JSONObject mainObj= new JSONObject();

        try {
            mainObj.put("to", "/topics/"+"news");

            JSONObject notificationObj = new JSONObject();

            notificationObj.put("title","Calling ....");
            notificationObj.put("body","Someone is calling ...");
            mainObj.put("notification",notificationObj);


            JSONObject extraData = new JSONObject();
            extraData.put("brandId","puma");
            extraData.put("category","Shoes");

            mainObj.put("notofication",notificationObj);
            mainObj.put("data",extraData);



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
    public void fetchSessionConnectionData() {
        System.out.println("this is count 6 "+count);
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(new JsonObjectRequest(Request.Method.GET,
                "https://videochatapp3.herokuapp.com" + "/session",
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (count < 1) {
                        API_KEY = response.getString("apiKey");
                        SESSION_ID = response.getString("sessionId");
                        TOKEN = response.getString("token");

                        Log.i(LOG_TAG, "API_KEY: " + API_KEY);
                        Log.i(LOG_TAG, "SESSION_ID: " + SESSION_ID);
                        Log.i(LOG_TAG, "TOKEN: " + TOKEN);
                        sessaion.setAPI_KEY(API_KEY);
                        sessaion.setSESSION_ID(SESSION_ID);
                        sessaion.setTOKEN(TOKEN);
                        System.out.println("this is count 7 " + count);
//                    sessaion.setResponse(response);

//                    reff.push().setValue(sessaion);
// Session Created
                        mSession = new Session.Builder(Main2Activity.this, API_KEY, SESSION_ID).build();
                        mSession.setSessionListener(Main2Activity.this);
                        mSession.connect(TOKEN);
                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(), "User is busy", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    } catch(JSONException error){
                        Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
            }
        }));
    }

    private Publisher mPublisher;
    private Subscriber mSubscriber;
    //    private Button cancel;
    private CircleImageView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        System.out.println("this is count 1 "+count);
//        if (getIntent().hasExtra("category")){
//            Intent intent = new Intent(Main2Activity.this,RecieveNotificationActivity.class);
//            intent.putExtra("category",getIntent().getStringExtra("category"));
//            intent.putExtra("brandId",getIntent().getStringExtra("brandId"));
//            startActivity(intent);
//        }
        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        reff= FirebaseDatabase.getInstance().getReference().child("Users");
        reff2= FirebaseDatabase.getInstance().getReference().child("Users2");
        requestPermissions();
       System.out.println("1");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    maxid = dataSnapshot.getChildrenCount();
                    System.out.println("this is count 2 " + count);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    count = dataSnapshot.getChildrenCount();
                    System.out.println("this is count 3 " + count);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("this is count 4 "+count);
        sessaion=new session();
        mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);
        mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container);
//        cancel=(Button)findViewById(R.id.cancel);
        cancel=(CircleImageView) findViewById(R.id.profile);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onStreamDestroyed(PublisherKit publisherKit, Stream stream );
//                finishAffinity();
//                System.exit(0);
                 mSession.disconnect();
                reff.removeValue();
                reff2.removeValue();
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("this is count 5 "+count);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


//    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
//    private void requestPermissions() {
//        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            // initialize view objects from your layout
//
//            mPublisherViewContainer = (FrameLayout) findViewById(R.id.publisher_container);
//            mSubscriberViewContainer = (FrameLayout) findViewById(R.id.subscriber_container);
//
//            // initialize and connect to the session
//
//            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
//            mSession.setSessionListener(this);
//            mSession.connect(TOKEN);
//
//
//        } else {
//            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
//        }
//    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = (FrameLayout) findViewById(R.id.publisher_container);
            mSubscriberViewContainer = (FrameLayout) findViewById(R.id.subscriber_container);
            System.out.println("this is count 11 "+count);
            // initialize and connect to the session
            fetchSessionConnectionData();

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }



// 1
    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);
        mPublisherViewContainer.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView){
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }
             if (maxid<=2) {
                 mSession.publish(mPublisher);
             }
             else {

                 Toast.makeText(getApplicationContext(),"User is busy",Toast.LENGTH_SHORT).show();
             }
//        reff.push().setValue(session);
    }

    @Override
    public void onDisconnected(Session session) {

        Log.i(LOG_TAG, "Session Disconnected");

    }
// 2
    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");
        System.out.println("THis is stream"+stream);
         if (maxid<2) {
             if (mSubscriber == null) {
                 mSubscriber = new Subscriber.Builder(this, stream).build();
                 mSession.subscribe(mSubscriber);
                 mSubscriberViewContainer.addView(mSubscriber.getView());
             }
         }

        else if(mSubscriber == null){
             System.out.println("this is count 8 "+count);
             cancel.setEnabled(false);
             Toast.makeText(getApplicationContext(),"User is busy",Toast.LENGTH_LONG).show();
             Intent intent = new Intent(Main2Activity.this, MainActivity.class);
             startActivity(intent);
         }

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");
        System.out.println("this is count 9 "+count);
        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();

        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    // 3
    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamCreated");
        sessaion.setStream(stream.getCreationTime());
        reff.child(String.valueOf(maxid+1)).setValue(sessaion);
        reff2.child(String.valueOf(count+1)).setValue(count);
        System.out.println("this is count 10 "+count);
//        if (maxid<1) {
//            sendNotification();
//        }
//        reff.push().setValue(sessaion);


    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamDestroyed");
        mSession.unpublish(mPublisher);
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
    }



}
