package com.example.rucafe;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.Toast;

/**
 * Donut Adapter class used to create the RecyclerView for the donut GUI.
 * @author Akshat Mehta and Atharva Patil
 */
public class MyDonutAdapter extends RecyclerView.Adapter<MyDonutAdapter.MyDonutViewHolder> {

    private String donutNameStrings[], donutPriceStrings[];
    private int images[];
    private Context context;

    /**
     * Donut Adapter constructor that initializes the private arrays.
     * @param ct context.
     * @param s1 String array for Donut Names.
     * @param s2 String array for Donut prices.
     * @param img Images of donuts.
     */
    public MyDonutAdapter(Context ct, String s1[], String s2[], int img[]){
        this.context = ct;
        this.donutNameStrings = s1;
        this.donutPriceStrings = s2;
        this.images = img;
    }

    /**
     * This method creates a new RecyclerView and initializes private
     * fields that are used by the RecyclerView
     * @param parent parent view that holds the cell you will create.
     * @param viewType the type of cell you will create.
     * @return MyDonutViewHolder donut view that will be created.
     */
    @NonNull
    @Override
    public MyDonutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyDonutViewHolder(view);
    }

    /**
     * Updates the contents of the Recycler View to reflect the item at a given position.
     * @param holder holder which is updated to represent the contents of the item at a given position.
     * @param position position of the item in the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyDonutViewHolder holder, int position) {
        holder.donut_names.setText(donutNameStrings[position]);
        holder.donut_prices.setText(donutPriceStrings[position]);
        holder.donut_images.setImageResource(images[position]);
    }

    /**
     * Gets the total number of items that are in the data set.
     * @return int number of items.
     */
    @Override
    public int getItemCount() {
        return donutNameStrings.length;
    }

    /**
     * Describes the items the in the Item View and about the items itself.
     */
    public class MyDonutViewHolder extends RecyclerView.ViewHolder{

        private TextView donut_names, donut_prices;
        private ImageView donut_images;
        private Button addDonut;
        private ConstraintLayout parentLayout;

        /**
         * Constructor for initializing different parts of the view.
         * @param itemView view that holds the items.
         */
        public MyDonutViewHolder(@NonNull View itemView) {
            super(itemView);
            donut_names = itemView.findViewById(R.id.donutNames);
            donut_prices = itemView.findViewById(R.id.donutPrices);
            donut_images = itemView.findViewById(R.id.donutImages);
            addDonut = itemView.findViewById(R.id.addDonut);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            donut_images.getLayoutParams().height = 300;
            donut_images.getLayoutParams().width = 300;

            setAddDonutOnClick(itemView);

            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DonutActivity.class);
                    intent.putExtra("Item", donut_names.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        /**
         * Once a donut is clicked, it asks the user if they want to add the donut to the ordering basket.
         * @param itemView view that holds the items.
         */
        private void setAddDonutOnClick(@NonNull View itemView) {
            addDonut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle("Add to order");
                    alert.setMessage(donut_names.getText().toString());

                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(),
                                    donut_names.getText().toString() + " added.", Toast.LENGTH_LONG).show();
                            String [] donutSplit = donut_names.getText().toString().split(" - ");
                            String name = donutSplit[0];
                            String flavor = donutSplit[1];
                            int quantity = 1;

                            Donut donut = new Donut(name, flavor, quantity);
                            MainActivity.addMenuItem(donut);
                        }
                        
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(),
                                    donut_names.getText().toString() + " not added.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }
    }
}
