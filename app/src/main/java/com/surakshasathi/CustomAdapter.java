package com.surakshasathi;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<DataHolder> dataList;

    public CustomAdapter(Context context, List<DataHolder> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Implement the view creation logic for each item in the list
        // Use convertView for better performance (recycling views)
        // Example:
        View view = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);

        TextView textView1 = view.findViewById(R.id.textView1);
//        TextView textView2 = view.findViewById(R.id.textView2);

        // Set data for each TextView
        DataHolder dataModel = dataList.get(position);
//        textView1.setText(dataModel.getText1());
//        textView2.setText(dataModel.getText2());
        String text = dataModel.getText1();
        String text2 = dataModel.getText2();
        String formattedText = text + "<font color='#145F9A'>" +" " +text2 + "</font>";

        // Set the formatted text to the TextView using HtmlCompat.fromHtml
        textView1.setText(HtmlCompat.fromHtml(formattedText, HtmlCompat.FROM_HTML_MODE_LEGACY));

        return view;
    }
}