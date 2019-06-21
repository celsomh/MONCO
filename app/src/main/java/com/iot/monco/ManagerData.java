package com.iot.monco;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.iot.monco.view.CardListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerData {

    private JSONObject jsonObject;
    private String[] idData;
    private Activity activity;
    private CardListAdapter cardListAdapter;


    public ManagerData(String[] idData, CardListAdapter cardListAdapter, Activity activity) {
        this.idData = idData;
        this.activity = activity;
        this.cardListAdapter = cardListAdapter;

    }

    public void setJSONObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        updateTextView();
    }

    private void updateTextView() {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> data = new ArrayList<>();
                String value;
                for (int i = 0; i < idData.length; i++) {
                    value = parseJSON(idData[i]);
                    data.add(value);
                }
                cardListAdapter.updateData(data);
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
