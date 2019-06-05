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
    private static String token;
    private static String refreshToken;

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
        StudentDataPrepare();

    }

    @Override
    protected void onStart() {
        super.onStart();

        runProcessGetToken();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void StudentDataPrepare() {
        Card data = new Card("ph", 8);
        cardDataList.add(data);
        data = new Card("Humedad", 24);
        cardDataList.add(data);
        data = new Card("Concentracion de gases", "medio");
        cardDataList.add(data);
    }


    private void runProcessGetToken() {
        Timer timer = new Timer();
        TokenTimerTask tokenTimerTask = new TokenTimerTask(URL_LOGIN, URL_REFRESH_TOKEN);
        timer.schedule(tokenTimerTask, 0l, 1000 * 60 * 14);

    }
}
