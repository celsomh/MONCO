package com.iot.monco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<Card> cardList = new ArrayList<>();
    private static final String URL_LOGIN = "http://endor.ceisufro.cl:8080/api/auth/login";
    private static final String URL_REFRESH_TOKEN = "http://endor.ceisufro.cl:8080/api/auth/token";
    private final String URL_DEVICE = "http://endor.ceisufro.cl:8080/api/plugins/telemetry/DEVICE/b97c9910-8807-11e9-9c7a-ef2ffff9109f/values/timeseries";
    private ManagerCard managerCard;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        cardAdapter = new CardAdapter(cardList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(cardAdapter);
        cardDataPrepare();
        timer = new Timer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }


    private void cardDataPrepare() {
        Card ph = new Card("humidity", "Humedad", "txthumedad");
        cardList.add(ph);
        Card humedad = new Card("gas", "Gas", "txtgas");
        cardList.add(humedad);
        managerCard = new ManagerCard(cardList, this);
    }


    private void startTimer() {
        TokenTimerTask tokenTimerTask = new TokenTimerTask(URL_LOGIN, URL_REFRESH_TOKEN);
        timer.schedule(tokenTimerTask, 0l, 1000 * 60 * 14);

        APIComunicator apiComunicator = new APIComunicator(URL_DEVICE, tokenTimerTask, managerCard);
        timer.schedule(apiComunicator, 2000, 1000);

    }
}