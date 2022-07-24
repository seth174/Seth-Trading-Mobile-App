package com.sethfagen.sethstradingapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends BaseAdapter implements Filterable {
    private List<String> list;
    private List<String> fullList;
    private Context context;

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> newList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                newList.addAll(fullList);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim();
                for(String item : fullList){
                    if(item.toLowerCase(Locale.ROOT).trim().contains(filterPattern)){
                        newList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = newList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public SearchAdapter(List<String> fullList, Context context) {
        this.fullList = fullList;
        this.list = new ArrayList<>(fullList);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       if(view == null){
           view = LayoutInflater.from(context).inflate(android.R.layout.simple_dropdown_item_1line, viewGroup, false);
       }

       return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
