package com.example.itadmin.cryptoexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Omo_Nosa on 11/10/2017.
 */

public class Conversion extends AppCompatActivity {

    double Eth, Btc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion);



        Intent intent = getIntent();
        String Curr = intent.getStringExtra("Curr");
        Btc = intent.getDoubleExtra("btcVal", 0);
        Eth = intent.getDoubleExtra("ethVal", 0);

        String fullName = getFullName(Curr) + " Conversion";

        //get the initial value TextView
        TextView currFullName = (TextView) findViewById(R.id.currency);
        currFullName.setText(fullName);

    }

    //Get full money name from curency code
    public String getFullName(String moneyCode) {
        switch (moneyCode) {
            case "NGN": return "Naira";
            case "USD": return "US Dollar";
            case "EUR": return "Euro";
            case "JPY": return "Yen";
            case "GBP": return "Pound Sterling";
            case "AUD": return "Australian Dollar";
            case "CAD": return "Canadian Dollar";
            case "CHF": return "Swiss Franc";
            case "CNY": return "Yuan";
            case "KES": return "Kenyan Shilling";
            case "GHS": return "Cedi";
            case "UGX": return "Ugandan Shilling";
            case "ZAR": return "Rand";
            case "XAF": return "CFA Franc BCEAO";
            case "NZD": return "New Zealand Dollar";
            case "MYR": return "Malaysian Ringgit";
            case "BND": return "Brunei Dollar";
            case "GEL": return "Lari";
            case "RUB": return "Russian Ruble";
            case "INR": return "Indian Rupee";
            default: return "";
        }


    }

    public void convert(View view) {

        EditText initValue = (EditText) findViewById(R.id.init_value);
        String flatValue = initValue.getText().toString();

        if (isEmpty(flatValue)) {

            Toast.makeText(this, "value cannot be empty", Toast.LENGTH_SHORT).show();

        } else {
            EditText btcValue = (EditText) findViewById(R.id.btc_value);
            btcValue.setText(String.valueOf(Btc * Integer.parseInt(flatValue)));

            EditText ethValue = (EditText) findViewById(R.id.eth_value);
            ethValue.setText(String.valueOf(Eth * Integer.parseInt(flatValue)));
        }
    }


}
