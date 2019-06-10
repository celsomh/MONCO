package com.iot.monco;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerData {

    private JSONObject jsonObject;
    private HashMap<String, TextView> textViewHashMap;
    private Activity activity;

    public ManagerData(HashMap<String, TextView> textViewHashMap, Activity activity) {
        this.textViewHashMap = textViewHashMap;
        this.activity = activity;
    }

    public void setJSONArray(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        updateTextView();
    }

    private void updateTextView() {
        this.activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                for (Map.Entry<String, TextView> entry : textViewHashMap.entrySet()) {


                    String value = parseJSON(entry.getKey());
                    if (value != null) {
                        entry.getValue().setText(value);
                        Log.i("value", value);
                    }

                }


            }

        });
    }

    public String parseJSON(String nameObjectJSON) {
        try {


            String strJSONObject = jsonObject.getString(nameObjectJSON);


            JSONArray jsonArray = new JSONArray(strJSONObject);

            JSONObject dataJSON = jsonArray.getJSONObject(0);

            String value = dataJSON.getString("value");

            return value;

        } catch (JSONException e) {
            Log.i("Error", e.getMessage());
        }
        return null;
    }


}
