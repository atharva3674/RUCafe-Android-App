package com.example.rucafe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import java.text.DecimalFormat;

/**
 * Coffee Activity class used to open the GUI for ordering a coffee.
 * Enables the user to select their size and add ins for their coffee and tells them the price.
 * @author Akshat Mehta and Atharva Patil
 */
public class CoffeeActivity extends AppCompatActivity implements OnItemSelectedListener{
    private TextView subTotal;
    private CheckBox milkCheckBox, creamCheckBox, syrupCheckBox, caramelCheckBox, whippedCreamCheckBox;
    private Button addCoffee;
    private Spinner coffeeSizes;
    private static final double INITIAL_PRICE = 0.00;
    private static final int INITIAL_QUANTITY = 1;
    private Coffee coffee = new Coffee("Short", INITIAL_QUANTITY);
    private double price;

    /**
     * Creates the GUI and displays that different features that allows the user to make their coffee.
     * @param savedInstanceState contains data activity recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_first);

        subTotal = findViewById(R.id.coffeeSubTotal);
        addCoffee = findViewById(R.id.addCoffee);

        String [] sizes = {"Short", "Tall", "Grande", "Venti"};
        coffeeSizes = findViewById(R.id.coffeeSize);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeSizes.setAdapter(adapter);
        coffeeSizes.setOnItemSelectedListener(this);

        initiateCheckboxes();

        creamCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCream();
            }
        });
        syrupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSyrup();
            }
        });
        milkCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMilk();
            }
        });
        caramelCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCaramel();
            }
        });
        whippedCreamCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWhippedCream();
            }
        });

        addCoffee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Coffee coffee2 = new Coffee((String) coffeeSizes.getSelectedItem(), 1);

                for(int i = 0; i < coffee.getAddIns().length; i++){
                    if(coffee.getAddIns()[i]) coffee2.add(i);
                }
                String message = coffee2.toString() + " - Added";
                MainActivity.addMenuItem(coffee2);

                if(creamCheckBox.isChecked()) creamCheckBox.toggle();
                if(syrupCheckBox.isChecked()) syrupCheckBox.toggle();
                if(milkCheckBox.isChecked()) milkCheckBox.toggle();
                if(caramelCheckBox.isChecked()) caramelCheckBox.toggle();
                if(whippedCreamCheckBox.isChecked()) whippedCreamCheckBox.toggle();

                for(int i = 0; i < coffee.getAddIns().length; i++){
                    if(coffee.getAddIns()[i]) coffee.remove(i);
                }
                coffeeSizes.setAdapter(adapter);
                subTotal.setText("1.69");

                Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Cream checkbox was checked or unchecked so perform actions to recalculate price and adjust add ins array.
     */
    private void onClickCream(){
        if(creamCheckBox.isChecked()){
            updatePriceForAddOns("yes");
            coffee.add(0);
        }
        else{
            updatePriceForAddOns("no");
            coffee.remove(0);
        }
    }

    /**
     * Syrup checkbox was checked or unchecked so perform actions to recalculate price and adjust add ins array.
     */
    private void onClickSyrup(){
        if(syrupCheckBox.isChecked()){
            updatePriceForAddOns("yes");
            coffee.add(1);
        }
        else{
            updatePriceForAddOns("no");
            coffee.remove(1);
        }
    }

    /**
     * Milk checkbox was checked or unchecked so perform actions to recalculate price and adjust add ins array.
     */
    private void onClickMilk(){
        if(milkCheckBox.isChecked()){
            updatePriceForAddOns("yes");
            coffee.add(2);
        }
        else{
            updatePriceForAddOns("no");
            coffee.remove(2);
        }
    }

    /**
     * Caramel checkbox was checked or unchecked so perform actions to recalculate price and adjust add ins array.
     */
    private void onClickCaramel(){
        if(caramelCheckBox.isChecked()){
            updatePriceForAddOns("yes");
            coffee.add(3);
        }
        else{
            updatePriceForAddOns("no");
            coffee.remove(3);
        }
    }

    /**
     * Whipped Cream checkbox was checked or unchecked so perform actions to recalculate price and adjust add ins array.
     */
    private void onClickWhippedCream(){
        if(whippedCreamCheckBox.isChecked()){
            updatePriceForAddOns("yes");
            coffee.add(4);
        }
        else{
            updatePriceForAddOns("no");
            coffee.remove(4);
        }
    }

    /**
     * Performs actions when coffee size is selected.
     * Updates the sub-total field to reflec the new price.
     * @param adapterView adapterView where the item was selected.
     * @param view view within the adapterView that was clicked.
     * @param position position of selected item.
     * @param l l is the row id of item that was selected.
     */
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        double basePrice = INITIAL_PRICE;
        for(int i = 0; i < coffee.getAddIns().length; i++) {
            if(coffee.getAddIns()[i]) basePrice += Coffee.ADD_ONS;
        }

        double tempPrice = Coffee.BASE_PRICE;
        DecimalFormat formatter = new DecimalFormat("0.00");
        if(text.equals("Short")) basePrice += tempPrice;
        else if(text.equals("Tall")) basePrice += tempPrice + Coffee.TALL;
        else if(text.equals("Grande")) basePrice += tempPrice + Coffee.GRANDE;
        else if(text.equals("Venti")) basePrice += tempPrice + Coffee.VENTI;

        subTotal.setText(formatter.format(basePrice));
    }

    /**
     * Decide to do nothing if none of the items were selected.
     * @param adapterView adapterView where the items are.
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        return;
    }

    /**
     * Initialize all the values of the checkboxes by getting their respective ids.
     */
    private void initiateCheckboxes(){
        milkCheckBox = findViewById(R.id.milkCheckBox);
        creamCheckBox = findViewById(R.id.creamCheckBox);
        syrupCheckBox = findViewById(R.id.syrupCheckBox);
        caramelCheckBox = findViewById(R.id.caramelCheckBox);
        whippedCreamCheckBox = findViewById(R.id.whippedCreamCheckBox);
    }

    /**
     * Determines total price of Coffee add ons
     * @param command command argument for if add on is selected or not.
     */
    private void updatePriceForAddOns(String command){
        price = Double.parseDouble((String) subTotal.getText());
        DecimalFormat formatter = new DecimalFormat("0.00");

        if(command.equals("yes")) price += (coffee.ADD_ONS);
        else price -= (coffee.ADD_ONS);

        subTotal.setText(formatter.format(price));
    }
}