package com.iot.monco;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ManagerCard {
    private List<Card> cardList;
    private JSONObject jsonObject;




    public ManagerCard(List<Card> cardList, Activity activity) {
        this.cardList = cardList;
    }

    public void setJSONArray(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        updateCards();
    }

    private void updateCards() {
        for (int i = 0; i < cardList.size(); i++) {
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
