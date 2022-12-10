package com.admin.grocergo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Courier extends AppCompatActivity {
    Button addNewCourierButton;
    RecyclerView courierRecyclerView;
    RecyclerView.LayoutManager courierLayoutManager;
    CourierAdapter courierAdapter;
    ArrayList<CourierModel> courierArrayList = new ArrayList<>();
    String COURIER_DATA_JSON_STRING, courier_data_json_string;
    ProgressDialog progressDialog;
    int countData = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        courierRecyclerView = (RecyclerView) findViewById(R.id.courierList);
        courierLayoutManager = new LinearLayoutManager(this);
        courierRecyclerView.setLayoutManager(courierLayoutManager);
        courierRecyclerView.setHasFixedSize(true);
        courierAdapter = new CourierAdapter(courierArrayList);
        courierRecyclerView.setAdapter(courierAdapter);
        progressDialog = new ProgressDialog(Courier.this);
        addNewCourierButton = findViewById(R.id.addCourierBtn);

        addNewCourierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Courier.this, CourierAdd.class);
                startActivity(intent);
            }
        });

        // Bottom Navigation Set
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.courier);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tracking:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.courier:
                        return true;

                    case R.id.supplystock:
                        startActivity(new Intent(getApplicationContext(), SupplyStock.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // Read Data
        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readCourierData();
            }
        }, 1000);
    }

    public void getJSON() {
        new BackgroundTask().execute();
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void readCourierData() {
        if(checkNetworkConnection()) {
            courierArrayList.clear();
            try {
                JSONObject object = new JSONObject(courier_data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");
                String courier_id, courier_name, courier_phone;

                while(countData < serverResponse.length()) {
                    JSONObject jsonObject = serverResponse.getJSONObject(countData);
                    courier_id = jsonObject.getString("courier_id");
                    courier_name = jsonObject.getString("courier_name");
                    courier_phone = jsonObject.getString("courier_phone");

                    courierArrayList.add(new CourierModel(courier_id, courier_name, courier_phone));
                    countData++;
                }
                countData = 0;
                courierAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBConstants.SERVER_GET_URL_COURIER;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((COURIER_DATA_JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(COURIER_DATA_JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            courier_data_json_string = result;
        }
    }
}