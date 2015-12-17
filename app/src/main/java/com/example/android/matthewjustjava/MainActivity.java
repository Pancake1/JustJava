package com.example.android.matthewjustjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.matthewjustjava.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText getName = (EditText)findViewById(R.id.name_field);
        String nameValue = getName.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id. whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id. chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox cinnamonCheckBox = (CheckBox) findViewById(R.id. cinnamon_checkbox);
        boolean hasCinnamon = cinnamonCheckBox.isChecked();

        CheckBox frenchVanillaCheckBox = (CheckBox) findViewById(R.id. french_vanilla_checkbox);
        boolean hasFrenchVanilla = frenchVanillaCheckBox.isChecked();

        CheckBox marshMallowCheckBox = (CheckBox) findViewById(R.id. marsh_mallow_checkbox);
        boolean hasMarshMallow = marshMallowCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate, hasCinnamon, hasFrenchVanilla, hasMarshMallow);
        String priceMessage = createOrderSummary(nameValue, price, hasWhippedCream, hasChocolate, hasCinnamon, hasFrenchVanilla, hasMarshMallow);
        displayMessage(priceMessage);

        /*displayMessage(priceMessage);*/
        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addwhippedCream, boolean addChocolate, boolean addCinnamon, boolean addfrenchVanilla, boolean addmarshMallow) {
        int basePrice = 5;

        if (addwhippedCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        if (addCinnamon) {
            basePrice = basePrice + 2;
        }

        if (addfrenchVanilla) {
            basePrice = basePrice + 2;
        }

        if (addmarshMallow) {
            basePrice= basePrice +3;
        }

        return quantity * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate, boolean addCinnamon, boolean addFrenchVanilla, boolean addMarshMallow)   {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream: " + addWhippedCream;
        priceMessage += "\nAdd Chocolate: " + addChocolate;
        priceMessage += "\nAdd Cinnamon : " + addCinnamon;
        priceMessage += "\nAdd French Vanilla : " + addFrenchVanilla;
        priceMessage += "\nAdd MarshMellow : " + addMarshMallow;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal : $" + price;
        priceMessage += "\nThank You!";
        return priceMessage;

    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {

            Toast.makeText(this, "You can not have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {

            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;

        }
        quantity = quantity - 1;
       displayQuantity(quantity);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
       orderSummaryTextView.setText(message);
    }


}
