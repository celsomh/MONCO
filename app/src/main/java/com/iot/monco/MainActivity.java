package com.iot.monco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<Card> cardDataList = new ArrayList<>();
    private static final String URL_LOGIN = "http://endor.ceisufro.cl:8080/api/auth/login";
    private static final String URL_REFRESH_TOKEN = "http://endor.ceisufro.cl:8080/api/auth/token";
    private final String URL_DEVICE = "http://endor.ceisufro.cl:8080/api/plugins/telemetry/DEVICE/c32500b0-7290-11e9-828d-5ba8155d5fc8/values/timeseries";


    private Timer timer;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        cardAdapter = new CardAdapter(cardDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(cardAdapter);
        cardDataPrepare();
        timer = new Timer();


    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimerTask();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void cardDataPrepare() {
        Card data = new Card("ph", 8);
        cardDataList.add(data);
        data = new Card("Humedad", 24);
        cardDataList.add(data);
        data = new Card("Concentración de gases", "medio");
        cardDataList.add(data);
    }


    private void startTimerTask() {

        TokenTimerTask tokenTimerTask = new TokenTimerTask(URL_LOGIN, URL_REFRESH_TOKEN);
        timer.schedule(tokenTimerTask, 2000, 1000 * 60);

        APIComunicator apiComunicator = new APIComunicator(URL_DEVICE, tokenTimerTask);
        timer.schedule(apiComunicator, 4000, 1000);

    }

}
