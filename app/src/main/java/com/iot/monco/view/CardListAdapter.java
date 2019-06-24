package com.iot.monco.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iot.monco.MainActivity;
import com.iot.monco.R;

import java.util.ArrayList;

public class CardListAdapter extends BaseAdapter {
    private String[] namesCards;
    private Context context;
    private static LayoutInflater inflater = null;
    private String[] data;
    private View view;

    public CardListAdapter(MainActivity mainActivity, String[] namesCards) {
        this.namesCards = namesCards;
        initializeData();
        context = mainActivity;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void initializeData() {
        data = new String[namesCards.length];
    }

    @Override
    public int getCount() {
        return namesCards.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public void updateData(ArrayList<String> data) {
        String string[] = data.toArray(new String[0]);
        this.data = string;
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        view = inflater.inflate(R.layout.card_list_item, null);

        holder.textViewTitulo = view.findViewById(R.id.id_textview_titulo);
        holder.textViewTitulo.setText(namesCards[position]);
        holder.textViewDato = view.findViewById(R.id.id_textview_dato);
        try {
            holder.textViewDato.setText(data[position]);
        } catch (Exception e) {
            holder.textViewDato.setText("");
        }
        return view;
    }

    public class Holder {
        TextView textViewTitulo;
        TextView textViewDato;
    }
}
