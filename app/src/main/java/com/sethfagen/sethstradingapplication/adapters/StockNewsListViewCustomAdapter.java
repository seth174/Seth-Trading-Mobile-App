package com.sethfagen.sethstradingapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sethfagen.sethstradingapplication.R;
import com.sethfagen.sethstradingapplication.remote_database.models.StockNews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StockNewsListViewCustomAdapter extends BaseAdapter {
    private Context context;
    private List<StockNews> list;

    public StockNewsListViewCustomAdapter(Context context, List<StockNews> list){
        this.context = context;
        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.listview_stock_news, viewGroup, false);
        }

        StockNews stockNews = list.get(i);

        TextView stockNewsDate = view.findViewById(R.id.textview_date);
        TextView stockNewsSource = view.findViewById(R.id.textview_source);
        TextView stockNewsHeadline = view.findViewById(R.id.textView_headline);

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(stockNews.getDate() * 1000);
        stockNewsDate.setText(String.valueOf(date));

        stockNewsSource.setText(stockNews.getSource());
        stockNewsHeadline.setText(stockNews.getHeadline());

        return view;

    }
}
