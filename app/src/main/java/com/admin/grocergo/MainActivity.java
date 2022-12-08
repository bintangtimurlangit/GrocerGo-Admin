package com.admin.grocergo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button buttonView;
    private Spinner spinner;
    private static final String[] paths = {
            "Preparing",
            "On Going",
            "Delivered"
    };

    public int selector = 1;

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                selector = 1;
                Toast.makeText(MainActivity.this, "Selector: " + selector, Toast.LENGTH_SHORT).show();
                break;

            case 1:
                selector = 2;
                Toast.makeText(MainActivity.this, "Selector: " + selector, Toast.LENGTH_SHORT).show();
                break;

            case 2:
                selector = 3;
                Toast.makeText(MainActivity.this, "Selector: " + selector, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Button
        buttonView = findViewById(R.id.viewTrackingButton);

        // Button Navigation Set
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "Selector: " + selector, Toast.LENGTH_SHORT).show();

                switch(selector) {
                    case 1:
                        Toast.makeText(MainActivity.this, "Selector: " + selector, Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(MainActivity.this, TrackingViewPreparing.class);
                        MainActivity.this.startActivity(myIntent);
                        break;

                    case 2:
                        Toast.makeText(MainActivity.this, "Selector: " + selector, Toast.LENGTH_SHORT).show();
                        Intent myIntent2 = new Intent(MainActivity.this, TrackingViewOngoing.class);
                        MainActivity.this.startActivity(myIntent2);
                        break;

                    case 3:
                        Toast.makeText(MainActivity.this, "Selector: " + selector, Toast.LENGTH_SHORT).show();
                        Intent myIntent3 = new Intent(MainActivity.this, TrackingViewDelivered.class);
                        MainActivity.this.startActivity(myIntent3);
                        break;

                    default:
                        Toast.makeText(MainActivity.this, "Selector: " + selector, Toast.LENGTH_SHORT).show();
                        Intent myIntentDefault = new Intent(MainActivity.this, TrackingViewPreparing.class);
                        MainActivity.this.startActivity(myIntentDefault);
                        break;
                }
            }
        });

        // Spinner Navigation Set
        spinner = (Spinner)findViewById(R.id.spinnerDropdownTracking);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Bottom Navigation Set
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.tracking);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tracking:
                        return true;

                    case R.id.courier:
                        startActivity(new Intent(getApplicationContext(), Courier.class));
                        overridePendingTransition(0, 0);
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
}