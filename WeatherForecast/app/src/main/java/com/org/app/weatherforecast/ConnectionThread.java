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

    private String mLon;//経度
    private String mLat;//緯度
    private URL mRequestUrl;//URL
    private JSONObject mResponseJson;//レスポンス

    public ConnectionThread(String lon, String lat) {
        mLon = lon;
        mLat = lat;
        String requestUrlText = REQUEST_API_URL + UNITS_TYPE_METRIC + APP_API_KEY;//URLへの接続
        mRequestUrl = stringToUrl(requestUrlText);
    }

    @Override
    public void run() {//このスレッドで動かす内容
        mResponseJson = requestConnect();
    }

    //StringからURLに変換
    private URL stringToUrl(String urlText) {
        URL requestUrl = null;
        try {
            requestUrl = new URL(urlText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestUrl;
    }

    //StreamからStringに変換
    private String streamToString(InputStream is) {
        BufferedReader br
                = new BufferedReader(
                new InputStreamReader(is));//inputStreamをBufferに
        StringBuilder sb = new StringBuilder();//BufferをStringStreamに変換
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);//一行ずつ読み込み
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();//１つの文字列としてreturn
    }

    //URLにリクエストしてレスポンスのJSONを取得
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
            requestConnection.connect();//URLに接続
            is = requestConnection.getInputStream();//inputStreamを取得
            responseParameter = streamToString(is);//streamから文字列に変換
            responseJson = new JSONObject(responseParameter);//文字列をJSONに変換
        } catch (IOException e) {
            Log.e(LOG_TAG, "Connection failed: " + e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONObject failed: " + e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();//streamをクローズ
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, e.toString());
                e.printStackTrace();
            }
            if (null != requestConnection) {
                requestConnection.disconnect();//接続終了
            }
        }
        return responseJson;
    }
}
