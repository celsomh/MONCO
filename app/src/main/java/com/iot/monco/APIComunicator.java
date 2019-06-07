package com.iot.monco;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.TimerTask;

public class APIComunicator extends TimerTask {

    private TokenTimerTask tokenTimerTask;
    private String uRLDevice;
    private String token;
    private ManagerCard managerCard;
    private JSONObject dataJSONObject;


    public APIComunicator(String uRLDevice, TokenTimerTask tokenTimerTask, ManagerCard managerCard) {

        this.uRLDevice = uRLDevice;
        this.tokenTimerTask = tokenTimerTask;
        this.managerCard = managerCard;
    }

    @Override
    public void run() {
        token = tokenTimerTask.getToken();

        if (token != null) {
            dataJSONObject = doRequest();
            if (dataJSONObject != null) {
                sendData(dataJSONObject);
            }
        }


    }

    private void sendData(JSONObject jsonObject) {
        managerCard.setJSONArray(jsonObject);
    }

    private JSONObject doRequest() {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader;
        int statusCode;

        try {
            URL url = new URL(uRLDevice);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("content-type", "application/json");
            urlConnection.setRequestProperty("X-Authorization", "Bearer " + token);

            urlConnection.connect();
            statusCode = urlConnection.getResponseCode();

            inputStream = urlConnection.getInputStream();

            if (statusCode == 200) {

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line + "\n");
                }
                bufferedReader.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject;
            }


        } catch (JSONException e) {
            Log.i("Error", e.getMessage());
        } catch (IOException e) {

        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }

            } catch (Exception e) {

            }
        }
        return null;
    }


}
