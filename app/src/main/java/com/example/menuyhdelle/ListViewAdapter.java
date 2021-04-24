package com.example.menuyhdelle;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    public ArrayList<Menu> productList;
    Activity activity;

    public ListViewAdapter(Activity activity, ArrayList<Menu> productList) {
        super();
        this.activity = activity;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView mDate;
        TextView mItem;
        TextView mCo2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();
            holder.mDate = (TextView) convertView.findViewById(R.id.date);
            holder.mItem = (TextView) convertView.findViewById(R.id.item);
            holder.mCo2 = (TextView) convertView.findViewById(R.id.co2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Menu item = productList.get(position);
        holder.mDate.setText(DateFormat.format("dd MMMM yyyy", item.getDate()).toString());
        holder.mItem.setText(item.getName());
        holder.mCo2.setText(item.getCO2().toString());
        return convertView;
    }
}