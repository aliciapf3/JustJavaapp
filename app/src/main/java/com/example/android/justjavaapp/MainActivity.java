/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjavaapp;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justjavaapp.R;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity==100) {
            //Show error message as a toast
            Toast.makeText(this, "You can not have more than 100 cups", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayquantity(quantity);


    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity==1) {
            //Show error message as a toast
            Toast.makeText(this, "You can not have less than 1 cup", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayquantity(quantity);


    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Find the user's Name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText(). toString();

        //Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure out if the user wants chocolate toppings
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * Calculates the price of the order.
     * @param addWhippedCream if the client wants whipped cream topping
     * @param addChocolate if the client wants chocolate topping
     * @return total price
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        //Add 1$ if client wants whipped cream topping
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        //Add 2$ if client wants chocolate topping
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        //Calculate the total order price
        int price = quantity * basePrice;
        return price;
    }


    /**
     * This method is called to create an order summary
     * @param name of the client
     *@param price of the order
     *@param addWhippedCream if the user wants whipped cream topping
     *@param addChocolate if the user wants chocolate topping
     * @return text summary
     */

    private String createOrderSummary (String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal = $" + price;
        priceMessage = priceMessage + " \nThank You!";
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayquantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



}
