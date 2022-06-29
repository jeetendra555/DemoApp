package com.jk.demoapp.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jk.demoapp.R;
import com.jk.demoapp.adapter.RecyclerViewUserDataAdapter;
import com.jk.demoapp.callbreaks.DeleteButtonOnClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity implements DeleteButtonOnClickListener {
    private RecyclerView rvList;
    private ArrayList<HashMap<String, String>> arrAddressDataInList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> arrAddressData = new ArrayList<>();
    private RecyclerViewUserDataAdapter recyclerViewUserDataAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RegisterActivity.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ListActivity.this);
        RegisterActivity.sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = RegisterActivity.sharedPreferences.getString("Address", "");
        if (json.isEmpty()) {
            Toast.makeText(ListActivity.this, "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
            }.getType();

            arrAddressData = gson.fromJson(json, type);
            for (int i = 0; i < arrAddressData.size(); i++) {
                arrAddressDataInList.add(arrAddressData.get(i));
            }
        }
        rvList = findViewById(R.id.rvAllList);
        recyclerViewUserDataAdapter = new RecyclerViewUserDataAdapter(ListActivity.this, arrAddressDataInList, this::onDeleteButtonClick);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ListActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(recyclerViewUserDataAdapter);
        recyclerViewUserDataAdapter.notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDeleteButtonClick(int pos) {
        arrAddressDataInList.remove(pos);
        recyclerViewUserDataAdapter.notifyDataSetChanged();
        MainActivity.arrAddressDataFinal.addAll(arrAddressDataInList);
        Gson gson = new Gson();
        String json = gson.toJson(arrAddressDataInList);
        SharedPreferences.Editor editor = RegisterActivity.sharedPreferences.edit();
        editor.putString("Address", json);
        editor.apply();

    }
}