package com.iot.monco;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Random;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {
    List cardDataList;

    public CardAdapter(List cards) {
        this.cardDataList = cards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder viewHolder, int i) {
        Card data = (Card) cardDataList.get(i);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        viewHolder.parent.setBackgroundColor(currentColor);
        viewHolder.titulo.setText(data.titulo);
        viewHolder.cuerpo.setText(String.valueOf(data.strCuerpo));
    }

    @Override
    public int getItemCount() {
        return cardDataList.size();
    }


}
