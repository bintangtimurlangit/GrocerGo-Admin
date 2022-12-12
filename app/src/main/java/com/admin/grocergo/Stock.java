package com.admin.grocergo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.admin.grocergo.Adapter.StockAdapter;

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

public class Stock extends AppCompatActivity {
    Button addNewSupplyStockButton;
    RecyclerView stockRecyclerView;
    RecyclerView.LayoutManager stockLayoutManager;
    StockAdapter stockAdapter;
    ArrayList<StockModel> stockArrayList = new ArrayList<>();
    String STOCK_DATA_JSON_STRING, stock_data_json_string;
    ProgressDialog progressDialog;

    int countData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        stockRecyclerView = (RecyclerView) findViewById(R.id.itemList);
        stockLayoutManager = new LinearLayoutManager(this);
        stockRecyclerView.setLayoutManager(stockLayoutManager);
        stockRecyclerView.setHasFixedSize(true);
        stockAdapter = new StockAdapter(stockArrayList);
        stockRecyclerView.setAdapter(stockAdapter);
        progressDialog = new ProgressDialog(Stock.this);
        addNewSupplyStockButton = findViewById(R.id.addStockBtn);

        addNewSupplyStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stock.this, SupplyStockAdd.class);
                startActivity(intent);
            }
        });

        // Read Data
        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { readStockData(); }
        }, 1000);
    }

    public void getJSON() { new BackgroundTask().execute(); }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void readStockData() {
        if(checkNetworkConnection()) {
            stockArrayList.clear();
            try {
                JSONObject object = new JSONObject(stock_data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");
                String item_id, item_name, item_stock, item_price;

                while(countData < serverResponse.length()) {
                    JSONObject jsonObject = serverResponse.getJSONObject(countData);
                    item_id = jsonObject.getString("item_id");
                    item_name = jsonObject.getString("item_name");
                    item_stock = jsonObject.getString("item_stock");
                    item_price = jsonObject.getString("item_price");

                    stockArrayList.add(new StockModel(item_id, item_name, item_stock, item_price));
                    countData++;
                }
                countData = 0;
                stockAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBConstants.SERVER_GET_URL_STOCK;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((STOCK_DATA_JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(STOCK_DATA_JSON_STRING + "\n");
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
            stock_data_json_string = result;
        }
    }
}