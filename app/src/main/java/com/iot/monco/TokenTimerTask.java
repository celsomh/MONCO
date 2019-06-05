package com.iot.monco;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

public class TokenTimerTask extends TimerTask {

    private String uRLLogin;
    private String uRLRefreshToken;
    private boolean firstRequest;
    private String token;
    private String refreshToken;

    public TokenTimerTask(String uRLLogin, String uRLRefreshToken) {
        this.uRLLogin = uRLLogin;
        this.uRLRefreshToken = uRLRefreshToken;
        firstRequest = true;
    }


    @Override
    public void run() {
        if (firstRequest)
            requestToken();
        else
            requestRefreshToken();


    }

    private String requestToken() {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedWriter bufferedWriter;
        int statusCode;

        try {
            URL url = new URL(uRLLogin);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("accept", "application/json");
            urlConnection.setRequestProperty("content-type", "application/json");
            String data = "{\"username\":\"c.martin01@ufromail.cl\",\"password\":\"amaterasu\"}";
            urlConnection.setRequestProperty("data", data);

            urlConnection.connect();

            outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();

            statusCode = urlConnection.getResponseCode();
            inputStream = urlConnection.getInputStream();

            if (statusCode == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int value = inputStreamReader.read();
                StringBuilder result = new StringBuilder();
                while (value != -1) {
                    char current = (char) value;
                    result.append(current);
                    value = inputStreamReader.read();
                }
                JSONObject jsonObject = new JSONObject(result.toString());

                token = jsonObject.getString("token");
                refreshToken = jsonObject.getString("refreshToken");
                firstRequest = false;

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
        return null;
    }

    private String requestRefreshToken() {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedWriter bufferedWriter;
        int statusCode;

        try {
            URL url = new URL(uRLRefreshToken);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("content-type", "application/json");
            urlConnection.setRequestProperty("x-authorization", "Bearer $oldtoken");
            String data = "{\"refreshToken\":\"" + refreshToken + "\"}";
            urlConnection.setRequestProperty("data", data);

            urlConnection.connect();

            outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();

            statusCode = urlConnection.getResponseCode();
            inputStream = urlConnection.getInputStream();

            if (statusCode == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int value = inputStreamReader.read();
                StringBuilder result = new StringBuilder();
                while (value != -1) {
                    char current = (char) value;
                    result.append(current);
                    value = inputStreamReader.read();
                }
                JSONObject jsonObject = new JSONObject(result.toString());

                token = jsonObject.getString("token");
                refreshToken = jsonObject.getString("refreshToken");
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
        return null;
    }

    public String getToken() {
        return token;
    }


}

