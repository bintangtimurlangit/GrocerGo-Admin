package com.admin.grocergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Settings extends AppCompatActivity {

    Button buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Init
        buttonLogOut = findViewById(R.id.logoutBtn);

        // Logout Button
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Bottom Navigation Set
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tracking:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                }
                return false;
            }
        });
    }
}