package com.admin.grocergo;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
import java.util.HashMap;
import java.util.Map;

public class CourierAdd extends AppCompatActivity {
    String COURIER_DATA_JSON_STRING, courier_data_json_string;
    String courier_id, courier_name, courier_phone;
    EditText courierId, courierName, courierPhone;
    Button addCourierButton, cancelAddCourierButton;
    Boolean valid = true;
    ProgressDialog progressDialog;
    ArrayList<CourierModel> courierArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_add);

        courierId = (EditText) findViewById(R.id.courierShow_id);
        courierName = (EditText) findViewById(R.id.courierAdd_name);
        courierPhone = (EditText) findViewById(R.id.courierAdd_phone);
        progressDialog = new ProgressDialog(this);
        addCourierButton = (Button) findViewById(R.id.insertBtn);
        cancelAddCourierButton = (Button) findViewById(R.id.cancelBtn);

        addCourierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courier_name = courierName.getText().toString();
                courier_phone = courierPhone.getText().toString();
                courier_id = courierId.getText().toString();

                if(TextUtils.isEmpty(courier_name)) {
                    courierName.setError("Courier name cannot be empty!");
                    valid = false;
                } else {
                    valid = true;
                    if(TextUtils.isEmpty(courier_phone)) {
                        courierPhone.setError("Courier phone cannot be empty!");
                        valid = false;
                    } else {
                        valid = true;
                    }
                }

                if(valid) {
                    progressDialog.setMessage("Loading");
                    progressDialog.show();
                    createCourierData(courier_id, courier_name, courier_phone);
                }
            }
        });

        cancelAddCourierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourierAdd.this, Courier.class);
                startActivity(intent);
            }
        });

        // Read Data for Courier ID
        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readCourierData();
            }
        }, 1000);
    }

    public void getJSON() { new BackgroundTask().execute(); }

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
                int countData = serverResponse.length() + 1;
                courierId.setText(String.valueOf(countData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void createCourierData(final String courier_id, final String courier_name, final String courier_phone) {
        if(checkNetworkConnection()) {
            progressDialog.show();
            getJSON();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBConstants.SERVER_POST_URL_COURIER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                getJSON();
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("OK")) {
                                    getJSON();
                                    Toast.makeText(getApplicationContext(), "Data successfully inserted!", Toast.LENGTH_SHORT).show();
                                } else {
                                    getJSON();
                                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getJSON();
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("courier_id", courier_id);
                    params.put("courier_name", courier_name);
                    params.put("courier_phone", courier_phone);
                    return params;
                }
            };

            VolleySingleton.getInstance(CourierAdd.this).addToRequestQue(stringRequest);

            Intent intent = new Intent(CourierAdd.this, Courier.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "No connection to database!", Toast.LENGTH_SHORT).show();
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