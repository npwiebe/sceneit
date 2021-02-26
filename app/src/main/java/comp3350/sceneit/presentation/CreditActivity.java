package comp3350.sceneit.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import comp3350.sceneit.R;
import comp3350.sceneit.logic.CreditManager;

public class CreditActivity extends AppCompatActivity implements View.OnClickListener {

    private String message;
    private EditText nameCard, numberCard, cvc, expDate, country, province,
            addressOne, city, postalCode, telephoneNumber, email;
    private TextView purchaseDisplay;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        //initialize buttons, text views, and edit texts
        //All the EditTexts are named the same as their ids
        purchaseDisplay = (TextView) findViewById(R.id.puchaseInfo);
        submitButton = (Button) findViewById(R.id.button);
        nameCard = (EditText) findViewById(R.id.nameCard);
        numberCard = (EditText) findViewById(R.id.numberCard);
        cvc = (EditText) findViewById(R.id.cvc);
        expDate = (EditText) findViewById(R.id.expDate);
        country = (EditText) findViewById(R.id.country);
        province = (EditText) findViewById(R.id.province);
        addressOne = (EditText) findViewById(R.id.addressOne);
        city = (EditText) findViewById(R.id.city);
        postalCode = (EditText) findViewById(R.id.postalCode);
        telephoneNumber = (EditText) findViewById(R.id.telephoneNumber);
        email = (EditText) findViewById(R.id.email);

        //get stuff from the bundle that was passed
        Bundle b = getIntent().getExtras();
        int price = b.getInt("price");
        int ticketsNum = b.getInt("ticketsNum");
        String movieTitle = b.getString("movieTitle");
        String theatre = b.getString("theatre");

        message = "Purchasing " + ticketsNum + " Tickets for " + movieTitle + " at " + theatre + " for $" + price;
        purchaseDisplay.setText(message);

        submitButton.setOnClickListener(this);

    }

    public void callAlert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set title
                .setTitle("Error")//Tell the user theres an error in the data
                //set message
                .setMessage(message)//setup with the message that gets passed
                //set the okay button for the user.
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //this does nothing but the ok button needs it to work correctly so it exists.
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();//show the alert on screen
    }

    @Override
    public void onClick(View v) {
        //This is a lot of statements to go through each field I know
        //Couldnt figure out a way to go through each field and also have a unique message.
        if (!CreditManager.validateCredit(numberCard.getText().toString())) {
            callAlert("Invalid Credit Card Number");
        } else if (!CreditManager.fieldFilled(nameCard.getText().toString())) {
            callAlert("Please fill in the name located on your Credit card");
        } else if (!CreditManager.fieldFilled(cvc.getText().toString())) {
            callAlert("Please fill in the cvc located on your Credit card");
        } else if (!CreditManager.checkDate(expDate.getText().toString())) {
            callAlert("This card may be expired, check your expiry date");
        } else if (!CreditManager.fieldFilled(country.getText().toString())) {
            callAlert("Please fill in your country");
        } else if (!CreditManager.fieldFilled(province.getText().toString())) {
            callAlert("Please fill in your Region/Province/State");
        } else if (!CreditManager.fieldFilled(addressOne.getText().toString())) {
            callAlert("Please fill in your Address One");
        } else if (!CreditManager.fieldFilled(cvc.getText().toString())) {
            callAlert("Please fill in the cvc located on your Credit card");
        } else if (!CreditManager.fieldFilled(cvc.getText().toString())) {
            callAlert("Please fill in your city");
        } else if (!CreditManager.fieldFilled(postalCode.getText().toString())) {
            callAlert("Please fill in your Postal Code");
        } else if (!CreditManager.fieldFilled(telephoneNumber.getText().toString())) {
            callAlert("Please fill in your Telephone");
        } else if (!CreditManager.fieldFilled(email.getText().toString())) {
            callAlert("Please fill in your email");
        } else {

            //This is here for when we link to the next activity. Like confirmation page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}