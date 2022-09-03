package com.example.rucafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.content.Intent;

/**
 * Donut Activity class used to open the GUI for ordering a donut.
 * Enables the user to select the donut they want and asks the user if they want to order that donut.
 * @author Akshat Mehta and Atharva Patil
 */
public class DonutActivity extends AppCompatActivity {

    private RecyclerView donutRecyclerView;

    private String donutNames[], donutPrices[];
    private int donutImages[] = {R.drawable.sugar, R.drawable.jelly, R.drawable.pumpkin, R.drawable.glazed,
            R.drawable.chocolate, R.drawable.vanilla, R.drawable.strawberry, R.drawable.redvelvet,
            R.drawable.powdered, R.drawable.lemon, R.drawable.cinnamon, R.drawable.blueberry};

    /**
     * Creates the GUI and displays that different donuts that the user can choose from.
     * @param savedInstanceState contains data activity recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donut_first);

        donutRecyclerView = findViewById(R.id.donutRecyclerView);

        donutNames = getResources().getStringArray(R.array.donut_names);
        donutPrices = getResources().getStringArray(R.array.donut_prices);

        MyDonutAdapter myDonutAdapter = new MyDonutAdapter(this, donutNames, donutPrices, donutImages);
        donutRecyclerView.setAdapter(myDonutAdapter);
        donutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}