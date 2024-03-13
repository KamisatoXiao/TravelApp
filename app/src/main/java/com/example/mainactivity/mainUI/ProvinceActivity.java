package com.example.mainactivity.mainUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProvinceActivity extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    private List<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        initData();
        listView=findViewById(R.id.listView);
        listView.setAdapter(adapter=new ListViewAdapter(this,items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_item=(String)parent.getItemAtPosition(position);
                SharedPreferences sp= getApplication().getSharedPreferences("index", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("province",selected_item);
                editor.commit();
                Intent intent=new Intent(ProvinceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        listView.setSelection(items.size()-1);
    }
    private void initData(){
        items=new ArrayList<>();
        items.add("北京");
        items.add("上海");
        items.add("天津");
        items.add("重庆");
        items.add("南昌");
        items.add("景德镇");
        items.add("杭州");
        items.add("绍兴");
    }
}