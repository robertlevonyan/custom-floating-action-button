package com.robertlevonyan.views.customfloatingactionbuttonsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionLayout;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton floatingActionButton = findViewById(R.id.customFAB);
        final FloatingActionLayout floatingActionLayout = findViewById(R.id.customFABL);
        final FloatingLayout floatingLayout = findViewById(R.id.floating_layout);

        final String text = "Next";
        floatingActionButton.setFabText(text);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        floatingLayout.setOnMenuExpandedListener(new FloatingLayout.OnMenuExpandedListener() {
            @Override
            public void onMenuExpanded() {
                Toast.makeText(MainActivity.this, "Expanded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMenuCollapsed() {
                Toast.makeText(MainActivity.this, "Collapsed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
