package com.admin.grocergo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    TrackingFragment trackingFragment = new TrackingFragment();
    CourierFragment courierFragment = new CourierFragment();
    SupplyStockFragment supplyStockFragment = new SupplyStockFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, trackingFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tracking:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, trackingFragment).commit();
                        return true;
                    case R.id.courier:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, courierFragment).commit();
                        return true;
                    case R.id.supplystock:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, supplyStockFragment).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}