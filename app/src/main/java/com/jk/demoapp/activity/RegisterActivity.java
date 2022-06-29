package com.jk.demoapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jk.demoapp.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etFirstName, etLastName, etEmailAddress, etContactNumber, etAddress;
    private AppCompatButton btnSave;
    private ArrayList<HashMap<String, String>> arrAddress = new ArrayList<>();
    public static SharedPreferences sharedPreferences;
    private ArrayList<HashMap<String, String>> arrAddressData = new ArrayList<>();
    private ArrayList<HashMap<String, String>> arrAddressDataFinal = new ArrayList<>();
    private boolean isClickedSaveAddressOrNot = true;
    private boolean isDuplicate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Address", "");
        if (json.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
            }.getType();

            arrAddressData = gson.fromJson(json, type);
            for (int i = 0; i < arrAddressData.size(); i++) {
                arrAddressDataFinal.add(arrAddressData.get(i));
            }

        }

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etContactNumber = findViewById(R.id.etContactNumber);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.requireNonNull(etFirstName.getText()).toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                } else if (Objects.requireNonNull(etLastName.getText()).toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                } else if (Objects.requireNonNull(etContactNumber.getText()).toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter contact number", Toast.LENGTH_SHORT).show();
                } else if (Objects.requireNonNull(etEmailAddress.getText()).toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter email address", Toast.LENGTH_SHORT).show();
                } else if (Objects.requireNonNull(etAddress.getText()).toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < arrAddressDataFinal.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();

                        map = arrAddressDataFinal.get(i);

                        String oldFirstName = map.get("firstname");
                        String oldLastName = map.get("lastname");
                        String oldMobile = map.get("mobile");
                        String oldEmail = map.get("email");
                        String oldAddress = map.get("address");

                        String newFirstName = etFirstName.getText().toString();
                        String newLastName = etLastName.getText().toString();
                        String newMobile = etContactNumber.getText().toString();
                        String newEmail = etEmailAddress.getText().toString();
                        String newAddress = etAddress.getText().toString();

                        if (Objects.requireNonNull(oldFirstName).equalsIgnoreCase(newFirstName) &&
                                Objects.requireNonNull(oldLastName).equalsIgnoreCase(newLastName) &&
                                Objects.requireNonNull(oldMobile).equalsIgnoreCase(newMobile) &&
                                Objects.requireNonNull(oldEmail).equalsIgnoreCase(newEmail) &&
                                Objects.requireNonNull(oldAddress).equalsIgnoreCase(newAddress)) {

                            isDuplicate = true;
                            break;
                        }
                    }

                    if (isDuplicate) {
                        Toast.makeText(RegisterActivity.this, "Already this Register Data is saved", Toast.LENGTH_SHORT).show();
                        isClickedSaveAddressOrNot = true;
                    } else {
                        if (etFirstName.getText().toString().length() != 0 &&
                                etLastName.getText().toString().length() != 0 &&
                                etContactNumber.getText().toString().length() != 0 &&
                                etEmailAddress.getText().toString().length() != 0 &&
                                etAddress.getText().toString().length() != 0) {

                            String firstNamae = etFirstName.getText().toString().trim();
                            String lastName = etLastName.getText().toString().trim();
                            String mobile = etContactNumber.getText().toString().trim();
                            String email = etEmailAddress.getText().toString().trim();
                            String address = etAddress.getText().toString().trim();

                            HashMap<String, String> map = new HashMap<>();
                            map.put("firstname", firstNamae);
                            map.put("lastname", lastName);
                            map.put("mobile", mobile);
                            map.put("email", email);
                            map.put("address", address);
                            if (arrAddressDataFinal.isEmpty()) {

                            } else {
                                for (int k = 0; k < arrAddressDataFinal.size(); k++) {
                                    arrAddress.add(k, arrAddressDataFinal.get(k));
                                }
                            }

                            arrAddress.add(map);
                            Gson gson = new Gson();
                            String json = gson.toJson(arrAddress);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Address", json);
                            editor.commit();
                            isClickedSaveAddressOrNot = false;
                            Toast.makeText(RegisterActivity.this, "successfully User saved!", Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Plz Enter all Register info...", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

    }
}