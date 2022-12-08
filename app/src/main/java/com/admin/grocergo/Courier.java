package com.admin.grocergo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Courier extends AppCompatActivity {
    public static Courier ma;
    List<CourierModel> courierListItems;
    private RecyclerView courierRecyclerView;
    private RecyclerView.Adapter courierAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        // Init
        courierRecyclerView = (RecyclerView) findViewById(R.id.courierList);
        courierRecyclerView.setLayoutManager(new LinearLayoutManager(Courier.this));
        progressDialog = new ProgressDialog(this);
        courierListItems = new ArrayList<>();
        ma = this;
        courierRefreshList();

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
    }

    public void courierRefreshList(){
        courierListItems.clear();
        courierAdapter = new CourierAdapter(courierListItems, getApplicationContext());
        courierRecyclerView.setAdapter(courierAdapter);
        Toast.makeText(Courier.this, "CourierAdapter has been set", Toast.LENGTH_SHORT).show();

        courierRecyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SELECT_COURIER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                // Toast.makeText(Courier.this, "Checkpoint 1", Toast.LENGTH_SHORT).show();
                try {
                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    Toast.makeText(Courier.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        CourierModel item = new CourierModel(
                                o.getString("id"),
                                o.getString("name"),
                                o.getString("phone")
                        );

                        courierListItems.add(item);
                        courierAdapter = new CourierAdapter(courierListItems, getApplicationContext());
                        courierRecyclerView.setAdapter(courierAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(Courier.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", "kl");
                return params;
            }
        };
        RequestHandler.getInstance(Courier.this).addToRequestQueue(stringRequest);
    }
}