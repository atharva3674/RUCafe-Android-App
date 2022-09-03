package com.example.rucafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Main Activity class used to open the main GUI for the RU Cafe.
 * Enables the user to order a donut or coffee or look at their ordering basket or store orders.
 * @author Akshat Mehta and Atharva Patil
 */
public class MainActivity extends AppCompatActivity{

    private ImageView orderDonut;
    private ImageView orderCoffee;
    private ImageView yourOrder;
    private ImageView storeOrder;
    private static Order order = new Order();
    private static StoreOrders storeOrders = new StoreOrders();
    private static double price = 0.00;
    private static ArrayList<Integer> orderNumbers = new ArrayList<>();
    private static int orderNum = 1;

    /**
     * Creates the GUI and displays the different parts of the Main GUI and initialization.
     * @param savedInstanceState contains data activity recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderDonut = findViewById(R.id.OrderDonut);
        orderDonut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DonutActivity.class);
                startActivity(intent);
            }
        });

        orderCoffee = findViewById(R.id.OrderCoffee);
        orderCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CoffeeActivity.class);
                startActivity(intent);
            }
        });

        yourOrder = findViewById(R.id.YourOrders);
        yourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, YourOrderActivity.class);
                startActivity(intent);
            }
        });

        storeOrder = findViewById(R.id.StoreOrders);
        storeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoreOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Sets the order.
     * @param orderTemp Order that was passed.
     */
    public static void setOrder(Order orderTemp){
        order = orderTemp;
    }

    /**
     * Gets the order.
     * @return Order order.
     */
    public static Order getOrder(){
        return order;
    }

    /**
     * Gets the store orders.
     * @return StoreOrders storeOrders.
     */
    public static StoreOrders getStoreOrders(){ return storeOrders; }

    /**
     * Gets the orderNumbers arraylist.
     * @return ArrayList<Integer> orderNumbers.
     */
    public static ArrayList<Integer> getOrderNumbers(){
        return orderNumbers;
    }
    /**
     * Adds the Menu Item to the order list.
     * @param item MenuItem item.
     */
    public static void addMenuItem(MenuItem item){
        for(MenuItem i : order.getOrderList()) {
            if (item instanceof Coffee && i instanceof Coffee && i.equals(item)) {
                i.quantity = i.getQuantity() + 1;
                return;

            }
            else if (item instanceof Donut && i instanceof Donut && i.equals(item)) {
                i.quantity = i.getQuantity() + 1;
                return;
                }
            }
        order.add(item);
    }

    /**
     * Removes the Menu Item from the order list.
     * @param item Menu item passed as a String.
     */
    public static void removeMenuItem(String item){
        for(MenuItem i : order.getOrderList()){
            if(i.toString().equals(item)) {
                order.remove(i);
                break;
            }
        }
    }

    /**
     * Removes the order from the store orders list.
     * @param orderNum order number for order to be removed.
     */
    public static void removeOrder(int orderNum){
        Order order = storeOrders.getStoreOrdersList().get(orderNum);
        storeOrders.remove(order);
    }

    /**
     * Finds the sub-total of all the Menu Items.
     * @return double price.
     */
    public static double findSubTotal(){
        double tempPrice = 0.00;
        for(MenuItem i : order.getOrderList()) {
            if (i instanceof Coffee) {
                tempPrice += i.itemPrice() * i.getQuantity();
            }
            else if (i instanceof Donut) {
                tempPrice += i.itemPrice() * i.getQuantity();
            }
        }
        price = tempPrice;
        return price;
    }

    /**
     * Add the order to storeOrders list.
     */
    public static void addOrder(){
        storeOrders.add(order);
    }

    /**
     * Find the individual totals for all orders in the storeOrders list.
     * @return String array that stores all the totals as Strings.
     */
    public static String[] findTotalForAllOrders(){
        String total[] = new String[storeOrders.getStoreOrdersList().size()];
        int count = 0;
        DecimalFormat formatter = new DecimalFormat("0.00");
        for(Order order : storeOrders.getStoreOrdersList()){
            double overallTotal = findSubTotalForOrder(order) + (findSubTotalForOrder(order) * YourOrderActivity.SALES_TAX);
            total[count] = formatter.format(overallTotal);
            count++;
        }
        return total;
    }

    /**
     * Find the sub-total given a specific order.
     * @param orderTemp Order orderTemp.
     * @return double price of the sub-total.
     */
    public static double findSubTotalForOrder(Order orderTemp){
        double tempPrice = 0.00;
        for(MenuItem i : orderTemp.getOrderList()) {
            if (i instanceof Coffee) {
                tempPrice += i.itemPrice() * i.getQuantity();
            }
            else if (i instanceof Donut) {
                tempPrice += i.itemPrice() * i.getQuantity();
            }
        }
        price = tempPrice;
        return price;
    }

    /**
     * New order was created, add the order number to orderNumbers arraylist.
     */
    public static void addOrderNumber(){
        orderNumbers.add(orderNum);
        orderNum++;
    }

    /**
     * Order was removed, remove the order number from orderNumbers arraylist
     * @param num order number position to remove.
     */
    public static void removeOrderNum(int num){
        orderNumbers.remove(num);
    }
}