package com.org.app.weatherforecast;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionThread implements Runnable {
    private static final String LOG_TAG = ConnectionThread.class.getSimpleName();
    private static final String REQUEST_API_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String UNITS_TYPE_METRIC = "&units=metric";
    private static final String APP_API_KEY = "&appid=33f53783f294971d4ba86beae6a2b356";

    private String mLon;
    private String mLat;
    private URL mRequestUrl;
    private JSONObject mResponseJson;

    public ConnectionThread(String lon, String lat) {
        mLon = lon;
        mLat = lat;
        String requestUrlText = REQUEST_API_URL + UNITS_TYPE_METRIC + APP_API_KEY;
        mRequestUrl = stringToUrl(requestUrlText);
    }

    @Override
    public void run() {
        mResponseJson = requestConnect();
    }

    private URL stringToUrl(String urlText) {
        URL requestUrl = null;
        try {
            requestUrl = new URL(urlText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestUrl;
    }

    private String streamToString(InputStream is) {
        BufferedReader br
                = new BufferedReader(
                new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private JSONObject requestConnect() {
        if (null == mRequestUrl) {
            return null;
        }
        HttpURLConnection requestConnection = null;
        String responseParameter;
        InputStream is = null;
        JSONObject responseJson = null;
        try {
            requestConnection = (HttpURLConnection) mRequestUrl.openConnection();
            requestConnection.connect();
            is = requestConnection.getInputStream();
            responseParameter = streamToString(is);
            responseJson = new JSONObject(responseParameter);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Connection failed: " + e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONObject failed: " + e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, e.toString());
                e.printStackTrace();
            }
            if (null != requestConnection) {
                requestConnection.disconnect();
            }
        }
        return responseJson;
    }



}
