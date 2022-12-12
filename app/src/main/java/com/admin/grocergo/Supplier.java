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

import com.admin.grocergo.Adapter.SupplierAdapter;

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

public class Supplier extends AppCompatActivity {
    Button addNewSupplyStockButton;
    RecyclerView supplierRecyclerView;
    RecyclerView.LayoutManager supplierLayoutManager;
    SupplierAdapter supplierAdapter;
    ArrayList<SupplierModel> supplierArrayList = new ArrayList<>();
    String SUPPLIER_DATA_JSON_STRING, supplier_data_json_string;
    ProgressDialog progressDialog;

    int countData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        supplierRecyclerView = (RecyclerView) findViewById(R.id.supplierList);
        supplierLayoutManager = new LinearLayoutManager(this);
        supplierRecyclerView.setLayoutManager(supplierLayoutManager);
        supplierRecyclerView.setHasFixedSize(true);
        supplierAdapter = new SupplierAdapter(supplierArrayList);
        supplierRecyclerView.setAdapter(supplierAdapter);
        progressDialog = new ProgressDialog(Supplier.this);
        addNewSupplyStockButton = findViewById(R.id.addSupplierBtn);

        addNewSupplyStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Supplier.this, SupplyStockAdd.class);
                startActivity(intent);
            }
        });

        // Read Data
        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { readSupplierData(); }
        }, 1000);
    }

    public void getJSON() { new BackgroundTask().execute(); }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void readSupplierData() {
        if(checkNetworkConnection()) {
            supplierArrayList.clear();
            try {
                JSONObject object = new JSONObject(supplier_data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");
                String supplier_id, supplier_name, supplier_email, item_id, item_name;

                while(countData < serverResponse.length()) {
                    JSONObject jsonObject = serverResponse.getJSONObject(countData);
                    supplier_id = jsonObject.getString("supplier_id");
                    supplier_name = jsonObject.getString("supplier_name");
                    supplier_email = jsonObject.getString("supplier_email");
                    item_id = jsonObject.getString("item_id");
                    item_name = jsonObject.getString("item_name");

                    supplierArrayList.add(new SupplierModel(supplier_id, supplier_name, supplier_email, item_id, item_name));
                    countData++;
                }
                countData = 0;
                supplierAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBConstants.SERVER_GET_URL_SUPPLIER;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((SUPPLIER_DATA_JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(SUPPLIER_DATA_JSON_STRING + "\n");
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
            supplier_data_json_string = result;
        }
    }
}