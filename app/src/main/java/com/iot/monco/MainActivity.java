package com.iot.monco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private static final String URL_LOGIN = "http://endor.ceisufro.cl:8080/api/auth/login";
    private static final String URL_REFRESH_TOKEN = "http://endor.ceisufro.cl:8080/api/auth/token";
    private final String URL_DEVICE = "http://endor.ceisufro.cl:8080/api/plugins/telemetry/DEVICE/b97c9910-8807-11e9-9c7a-ef2ffff9109f/values/timeseries";
    <String, TextView> idTextView;
    private TextView humedad;
    private TextView gas;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        humedad = findViewById(R.id.id_textview_humedad);
        gas = findViewById(R.id.id_textview_gas);
        idTextView.put("humedad", humedad);
        idTextView.put("gas", gas);
        timer = new Timer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }


    private void startTimer() {
        TokenTimerTask tokenTimerTask = new TokenTimerTask(URL_LOGIN, URL_REFRESH_TOKEN);
        timer.schedule(tokenTimerTask, 0l, 1000 * 60 * 14);

        APIComunicator apiComunicator = new APIComunicator(URL_DEVICE, tokenTimerTask);
        timer.schedule(apiComunicator, 2000, 1000);

    }
}