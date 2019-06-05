package com.iot.monco;

import android.media.session.MediaSession;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

public class APIComunicator extends TimerTask {

    private String uRLDevice;
    private String token;

    public APIComunicator(String uRLDevice, String token) {
        this.uRLDevice = uRLDevice;
        this.token = token;
    }

    @Override
    public void run() {
        if (token != null)
            doGet();
    }

    private void doGet() {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader;
        int statusCode;

        try {
            URL url = new URL(uRLDevice);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("content-type", "application/json");
            urlConnection.setRequestProperty("x-authorization", "Bearer " + token);

            urlConnection.connect();

            statusCode = urlConnection.getResponseCode();
            inputStream = urlConnection.getInputStream();

            if (statusCode == 200) {

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line + "\n");
                }
                bufferedReader.close();
                Log.i("Result ", result.toString());
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
