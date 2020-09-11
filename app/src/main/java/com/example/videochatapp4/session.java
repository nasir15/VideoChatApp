package com.example.videochatapp4;

import org.json.JSONObject;

import java.util.Date;
import java.util.stream.Stream;

public class session {

String API_KEY;
String SESSION_ID;
String TOKEN;
Date stream;
    public Date getStream() {
        return stream;
    }

    public void setStream(Date stream) {
        this.stream = stream;
    }


//    JSONObject response;

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public String getSESSION_ID() {
        return SESSION_ID;
    }

    public void setSESSION_ID(String SESSION_ID) {
        this.SESSION_ID = SESSION_ID;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

//    public JSONObject getResponse() {
//        return response;
//    }
//
//    public void setResponse(JSONObject response) {
//        this.response = response;
//    }
}
