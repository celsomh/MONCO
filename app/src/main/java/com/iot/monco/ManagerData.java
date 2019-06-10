package com.iot.monco;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ManagerData {

    private JSONObject jsonObject;
    private HashMap hashMap;

    public ManagerData(HashMap hashMap) {
        this.hashMap=hashMap;
    }

    public void setJSONArray(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        updateCards();
    }

    private void updateCards() {
        for (int i = 0; i < hashMap.size(); i++) {
            hashMap.
            Card card = cardList.get(i);
            String id = card.getId();
            String value = parseJSON(id);
            if (value != null) {
                card.strCuerpo = value;
                Log.i("value", value);
            }
        }
    }

    public String parseJSON(String nameObjectJSON) {
        try {


            String strJSONObject = jsonObject.getString(nameObjectJSON);
            Log.i("nameObjectJSON", nameObjectJSON);

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
