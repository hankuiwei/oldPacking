package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/9/15.
 */
public class HomeSpinnerAdapter extends ArrayAdapter {
    private ArrayList<String> data;
    private Context context;

    public HomeSpinnerAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.item_spinner_select, android.R.id.text1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spinner_selected, null, false);
        TextView item = (TextView) view.findViewById(android.R.id.text1);
        item.setText(data.get(position));
//        if (position % 2 == 1) {
//            view.setBackgroundColor(context.getResources().getColor(R.color.gray_F8F8F8));
//        } else {
        view.setBackgroundColor(context.getResources().getColor(R.color.white));
//        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.item_spinner_select, null, false);
        TextView item = (TextView) view.findViewById(android.R.id.text1);
        item.setText(data.get(position));
        if (position % 2 == 1) {
            view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_0dpcorner_gray_bg));
        } else {
            view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_0dpcorner_white_bg));
        }
        return view;
    }

    public void clear() {
        this.data.clear();
    }

    public void addAll(ArrayList<String> data) {
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }
}
