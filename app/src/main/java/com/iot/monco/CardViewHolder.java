package com.iot.monco;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardViewHolder extends RecyclerView.ViewHolder {

    TextView titulo, cuerpo;
    LinearLayout parent;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView.findViewById(R.id.id_linearlayout_parent);
        titulo = itemView.findViewById(R.id.id_textview_titulo);
        cuerpo = itemView.findViewById(R.id.id_listview_cuerpo);
    }
}
