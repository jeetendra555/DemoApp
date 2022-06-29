package com.jk.demoapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jk.demoapp.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btnRegister, btnList;
    public static SharedPreferences sharedPreferences;
    private ArrayList<HashMap<String, String>> arrAddressData = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> arrAddressDataFinal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting view

        btnList = findViewById(R.id.btnList);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterActivity.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                RegisterActivity.sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = RegisterActivity.sharedPreferences.getString("Address", "");
                if (json.isEmpty()) {
                    Toast.makeText(MainActivity.this, "There is something error", Toast.LENGTH_LONG).show();
                } else {
                    Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
                    }.getType();

                    arrAddressData = gson.fromJson(json, type);
                    for (int i = 0; i < arrAddressData.size(); i++) {
                        arrAddressDataFinal.add(arrAddressData.get(i));
                    }
                }
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });

    }
}