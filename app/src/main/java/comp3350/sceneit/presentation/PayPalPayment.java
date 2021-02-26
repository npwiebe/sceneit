package comp3350.sceneit.presentation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp3350.sceneit.R;

public class PayPalPayment extends AppCompatActivity {

    private TextView totalPrice1;
    private TextView totalPrice2;
    private TextView cardInfo;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pay_pal_payment);
        totalPrice1 = (TextView) findViewById(R.id.totalPrice1);
        totalPrice2 = (TextView) findViewById(R.id.totalPrice2);
        cardInfo = (TextView) findViewById(R.id.cardInfo);

        //bundle get the total purchase price
        Bundle b = getIntent().getExtras();
        price = b.getInt("price");
        totalPrice1.setText("$"+Integer.toString(price)+".00");
        totalPrice2.setText("$"+Integer.toString(price)+".00");

        //get updated card info
        Intent intent = getIntent();
        String car_num = intent.getStringExtra("card_num");
        if(car_num!=null)
            cardInfo.setText("Card #: xxxx-"+car_num.substring(12,16));

    }

    public void placeOrder(View view) {
        Intent intent = new Intent(this,PayPalConfirm.class);
        startActivity(intent);
    }

    public void changePayment(View view) {
        Intent intent = new Intent(this,addCreditCard.class);
        startActivity(intent);
    }


}