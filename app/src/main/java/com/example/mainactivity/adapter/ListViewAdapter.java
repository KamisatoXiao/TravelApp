package com.example.mainactivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private List<String> data;
    private LayoutInflater inflater;
    public ListViewAdapter(Context context,List<String> data){
        inflater=LayoutInflater.from(context);
        this.data=data;
    }
    //数据源长度
    public int getCount(){
        return data.size();
    }

    //每一行绑定数据源
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //获取每一行的View
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            viewHolder.text1=convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.text1.setText(data.get(position));
        return convertView;
    }
    public class ViewHolder{
        private TextView text1;
    }
}
