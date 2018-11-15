package com.sharnit.banglalinkqms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sharnit.banglalinkqms.Model.Display;
import com.sharnit.banglalinkqms.R;

import java.util.ArrayList;

public class DisplayAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Display> displayArrayList;

    public DisplayAdapter(Context context, ArrayList<Display> displayArrayList) {
        this.context = context;
        this.displayArrayList = displayArrayList;
    }

    @Override
    public int getCount() {
        return displayArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return displayArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.display_adapter, viewGroup, false);
        }
        Display display = displayArrayList.get(i);

        TextView textViewCounter = (TextView)convertView.findViewById(R.id.tv_counter);
        TextView textViewToken = (TextView)convertView.findViewById(R.id.tv_token);

        textViewCounter.setText(display.getCounter());
        textViewToken.setText(display.getToken());



        return convertView;
    }
}
