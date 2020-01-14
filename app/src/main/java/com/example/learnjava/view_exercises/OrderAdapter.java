package com.example.learnjava.view_exercises;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class OrderAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if ( convertView == null){
//            convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
//        }
//
//        ((TextView) convertView.findViewById(android.R.id.text1))
//                .setText(getItem(position));
        return convertView;
    }
}
