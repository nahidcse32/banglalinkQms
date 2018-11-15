package com.sharnit.banglalinkqms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sharnit.banglalinkqms.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ServiceType> serviceTypeArrayList = new ArrayList<>();

    public GridAdapter(Context context, ArrayList<ServiceType> serviceTypeArrayList) {
        this.context = context;
        this.serviceTypeArrayList = serviceTypeArrayList;
    }

    @Override
    public int getCount() {
        return serviceTypeArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return serviceTypeArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_adapter, viewGroup, false);
        }


        ServiceType serviceType = serviceTypeArrayList.get(i);

        TextView textViewService = (TextView) view.findViewById(R.id.tv_service_types);
        textViewService.setText(serviceType.getName());
        return view;
    }
}
