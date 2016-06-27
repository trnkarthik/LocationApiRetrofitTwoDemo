package com.karthiktangirala.locationdemo.locationapiretrofittwodemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by karthiktangirala on 6/25/16.
 */

class ListAdapter extends ArrayAdapter<String> {

    private final List<String> list;
    private LayoutInflater layoutInflater;

    ListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.layoutInflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_address, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.address_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.textView.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView textView;

    }
}
