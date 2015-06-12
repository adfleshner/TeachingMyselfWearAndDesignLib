package com.flesh.understandingwear.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flesh.understandingwear.MyListAdapter;
import com.flesh.understandingwear.R;
import com.flesh.understandingwear.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Set;

public class List extends AppCompatActivity {


    RecyclerView list;
    Button add, sub;
    MyListAdapter adapter;

    ArrayList<String> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        setupList();
        addAllItems();
    }

    private void setupList() {
        //layoutManager
        if (DeviceUtils.isTablet(this)) {
            list.setLayoutManager(new GridLayoutManager(this, GridLayoutManager.DEFAULT_SPAN_COUNT));
        } else {
            list.setLayoutManager(new LinearLayoutManager(this));
        }
        //adapter
        adapter = new MyListAdapter(new ArrayList<String>());

        adapter.setOnItemClickListener(new MyListAdapter.onItemClickListener() {
            @Override
            public void onClick(String listItemClicked) {
                String clickedClassStr = "com.flesh.understandingwear.activities.items." + listItemClicked;
                try {

                    Class<?> clickedClass = Class.forName(clickedClassStr);
                    Intent i = new Intent(List.this, clickedClass);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(List.this, "No Class Found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        list.setAdapter(adapter);
    }

    //init view
    private void initView() {
        list = (RecyclerView) findViewById(R.id.list);
        add = (Button) findViewById(R.id.btn_add);
        sub = (Button) findViewById(R.id.btn_sub);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add("Notify");
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adapter.remove("Notify");
                } catch (ArrayIndexOutOfBoundsException e) {
                    Toast.makeText(List.this, "Nothing to Remove", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //org.jfrog.jade.plugins.common pulled so Reflections lib does not work.
    public void addAllItems() {
//        Reflections reflections = new Reflections("com.flesh.understandingwear.activities.items.");
//
//        Set<Class<? extends Object>> allClasses =
//                reflections.getSubTypesOf(Object.class);
        items.add("Notify");
        items.add("Demand");
        items.add("Handheld");
        items.add("Check");
        items.add("Coor");
        for (String s : items)
            adapter.add(s);

    }
}
