package com.admin.grocergo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.z2wenfa.spinneredittext.SpinnerEditText;

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

public class SupplyStockAdd extends AppCompatActivity {
    String SUPPLYSTOCK_DATA_JSON_STRING, supplystock_data_json_string;
    String supplier_id, supplier_name, supplier_email;
    String item_id, item_name, item_stock, item_price;
    EditText supplierName, supplierEmail, itemId, itemName, itemStock, itemPrice;
    TextView itemImageName;
    Button addNewInvButton, cancelAddInvButton, uploadInvImageButton;
    Boolean valid = true;
    ProgressDialog progressDialog;
    ArrayList<SupplyStockModel> supplyStockArrayList = new ArrayList<>();
    ArrayList<SupplierAltModel> supplierArrayList = new ArrayList<>();
    SpinnerEditText<ArrayList> supplierId;

    int countData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_stock_add);

        supplierName = (EditText) findViewById(R.id.supplierAdd_name);
        supplierEmail = (EditText) findViewById(R.id.supplierAdd_email);
        itemId = (EditText) findViewById(R.id.itemAdd_id);
        itemName = (EditText) findViewById(R.id.itemAdd_name);
        itemStock = (EditText) findViewById(R.id.itemAdd_stock);
        itemPrice = (EditText) findViewById(R.id.itemAdd_price);
        itemImageName = (TextView) findViewById(R.id.itemImage_name);

        uploadInvImageButton = (Button) findViewById(R.id.uploadBtn);
        addNewInvButton = (Button) findViewById(R.id.insertBtn);
        cancelAddInvButton = (Button) findViewById(R.id.cancelBtn);

        addNewInvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplier_id = supplierId.getText().toString();
                supplier_name = supplierName.getText().toString();
                supplier_email = supplierEmail.getText().toString();
                item_id = itemId.getText().toString();
                item_name = itemName.getText().toString();
                item_stock = itemStock.getText().toString();
                item_price = itemPrice.getText().toString();

                if(TextUtils.isEmpty(supplier_id)) {
                    supplierId.setError("Supplier ID cannot be empty!");
                    valid = false;
                } else {
                    valid = true;
                    if (TextUtils.isEmpty(supplier_name)) {
                        supplierName.setError("Supplier name cannot be empty!");
                        valid = false;
                    } else {
                        valid = true;
                        if (TextUtils.isEmpty(supplier_email)) {
                            supplierEmail.setError("Supplier email cannot be empty!");
                            valid = false;
                        } else {
                            valid = true;
                            if (TextUtils.isEmpty(item_id)) {
                                itemId.setError("Item ID cannot be empty!");
                                valid = false;
                            } else {
                                valid = true;
                                if (TextUtils.isEmpty(item_name)) {
                                    itemName.setError("Item name cannot be empty!");
                                    valid = false;
                                } else {
                                    valid = true;
                                    if (TextUtils.isEmpty(item_stock)) {
                                        itemName.setError("Item stock cannot be empty!");
                                        valid = false;
                                    } else {
                                        valid = true;
                                        if (TextUtils.isEmpty(item_price)) {
                                            itemPrice.setError("Item price cannot be empty!");
                                            valid = false;
                                        } else {
                                            valid = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if(valid) {
                    progressDialog.setMessage("Loading");
                    progressDialog.show();
//                    createSupplyStockData(supplier_id, supplier_name, supplier_email,
//                            item_id, item_name, item_stock, item_price);
                }
            }
        });

        cancelAddInvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplyStockAdd.this, SupplyStock.class);
                startActivity(intent);
            }
        });

        // Read Data for Courier ID
        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readSupplierData();
            }
        }, 1000);

        initSupplierSpinnerID();
    }

    private void initSupplierSpinnerID() {
        supplierId = (SpinnerEditText<ArrayList>) findViewById(R.id.supplierSpinner_id);
        supplierId.setRightCompoundDrawable(com.z2wenfa.spinneredittext.R.drawable.vector_drawable_arrowdown);

        supplierId.setNeedShowSpinner(true);
        // supplierId.setList(supplierIDList);
        supplierId.setSelection(0);

    }

    public static class IDSupplier {
        public String supplier_id;

        @Override
        public String toString() { return supplier_id; }
    }

    public void getJSON() { new SupplyStockAdd.BackgroundTask().execute(); }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void readSupplierData() {
        if(checkNetworkConnection()) {
           supplierArrayList.clear();
            try {
                JSONObject object = new JSONObject(supplystock_data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");

                for(int i = 0; i < serverResponse.length(); i++) {
                    JSONObject jsonObject = serverResponse.getJSONObject(i);
                    // supplierIDList.add(jsonObject.optString("supplier_id"));
                }

                String supplier_ol_id, supplier_ol_name, supplier_ol_email;

                while(countData < serverResponse.length()) {
                    JSONObject jsonObject = serverResponse.getJSONObject(countData);
                    supplier_ol_id = jsonObject.getString("supplier_id");
                    supplier_ol_name = jsonObject.getString("supplier_name");
                    supplier_ol_email = jsonObject.getString("supplier_email");

                    supplierArrayList.add(new SupplierAltModel(supplier_ol_id, supplier_ol_name, supplier_ol_email));
                    countData++;
                }
                countData = 0;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBConstants.SERVER_GET_URL_SUPPLYSTOCK;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((SUPPLYSTOCK_DATA_JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(SUPPLYSTOCK_DATA_JSON_STRING + "\n");
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
            supplystock_data_json_string = result;
        }
    }
}
