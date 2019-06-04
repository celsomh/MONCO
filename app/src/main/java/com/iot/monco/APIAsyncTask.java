package com.iot.monco;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIAsyncTask extends AsyncTask<String, String, String> {


    public APIAsyncTask() {

    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedWriter bufferedWriter;
        int statusCode;
        String output = null;
        try {
            URL url = new URL(urlString);
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
                output = jsonObject.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    @Override
    protected void onPostExecute(String param) {

    }
}
