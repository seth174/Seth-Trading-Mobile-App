package com.sethfagen.sethstradingapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sethfagen.sethstradingapplication.R;
import com.sethfagen.sethstradingapplication.remote_database.models.StockOverviewInfo;

import java.util.List;

public class StockListViewCustomAdapter extends BaseAdapter {
    private Context context;
    private List<StockOverviewInfo> list;

    public StockListViewCustomAdapter(Context context, List<StockOverviewInfo> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_stocks, parent, false);
        }
        StockOverviewInfo info = list.get(position);

        TextView textTicker = convertView.findViewById(R.id.textview_ticker);
        TextView textCurrentPrice = convertView.findViewById(R.id.textview_current_price);
        TextView textDailyGain = convertView.findViewById(R.id.textview_daily_gain);
        TextView textDailyPercentChange = convertView.findViewById(R.id.textview_daily_percent_change);
        TextView textQuantity = convertView.findViewById(R.id.textview_quantity);
        TextView textBuyingPrice = convertView.findViewById(R.id.textview_buying_price);
        TextView textTotalAmount = convertView.findViewById(R.id.textview_total_amount);
        TextView textNetGain = convertView.findViewById(R.id.textview_net_gain);
        TextView textPercentChange = convertView.findViewById(R.id.textview_percent_change);

        textTicker.setText(info.getTicker());
        textCurrentPrice.setText(String.valueOf(info.getCurrentPrice()));
        textDailyGain.setText(String.valueOf(info.getDailyNetChange()));
        textDailyPercentChange.setText(String.valueOf(info.getDailyPercentChange()));
        textQuantity.setText(String.valueOf(info.getQuantity()));
        textBuyingPrice.setText(String.valueOf(info.getPricePerShare()));
        textTotalAmount.setText(String.valueOf(info.getPrice()));
        textNetGain.setText(String.valueOf(info.getNetGain()));
        textPercentChange.setText(String.valueOf(info.getPercentChange()));

        return convertView;
    }
}
