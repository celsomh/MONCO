package com.iot.monco;

import android.media.session.MediaSession;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

public class APIComunicator extends TimerTask {
    private TokenTimerTask tokenTimerTask;
    private String uRLDevice;
    private String token;

    public APIComunicator(String uRLDevice, TokenTimerTask tokenTimerTask) {
        this.uRLDevice = uRLDevice;
        this.tokenTimerTask = tokenTimerTask;
    }

    @Override
    public void run() {
        token = tokenTimerTask.getToken();

        if (token != null)
            doRequest();

    }

    private void doRequest() {
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
            Log.i("Resul", "statuscode " + statusCode);
            inputStream = urlConnection.getInputStream();
            Log.i("Resul", "inputStream");
            if (statusCode == 200) {
                Log.i("Resul", "condicion");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line + "\n");
                }
                bufferedReader.close();
                Log.i("Result ", response.toString());
            }

        } catch (Exception e) {

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
    }
}
