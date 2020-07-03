package com.example.currencyconverter;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static API_SERVICES api_services;
    private ProgressDialog progressDialog;
    private float amountEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] currencies = {"CAD","MYR","KRW","GBP","ILS","MXN","USD", "ZAR","NZD", "NOK", "CNY", "TRY", "BGN", "PLN","SGD","CHF",
                "THB", "JPY", "HRK", "RUB", "BRL", "INR", "IDR", "SEK", "RON", "AUD", "CZK", "HUF", "DKK", "PHP", "ISK", "HKD"};

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, currencies);

        //Setting the data to the to currency spinner to be displayed
        final Spinner toCurrency = findViewById(R.id.toSpinner);
        toCurrency.setAdapter(adapter);

        //Setting the data to the from currency spinner to be displayed..
        Spinner fromCurrency = findViewById(R.id.fromSpinner);
        fromCurrency.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Converting");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);

        Button convertbtn = findViewById(R.id.convertBtn);

        convertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amount_entered = findViewById(R.id.amount_entered);
                String amount = amount_entered.getText().toString();

                if(amount.trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter amount to convert", Toast.LENGTH_SHORT).show();
                }else {
                    amountEntered = Float.valueOf(amount);
                    progressDialog.show();
                    api_services = API_CLIENT.getApiClient().create(API_SERVICES.class);
                    Call<List<Model_Class>> call = api_services.currency();
                    call.enqueue(new Callback<List<Model_Class>>() {
                        @Override
                        public void onResponse(Call<List<Model_Class>> call, Response<List<Model_Class>> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Currency load not successful", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Currency api loaded", Toast.LENGTH_LONG).show();
//                                String selectedCurrency = toCurrency.getSelectedItem().toString();
//                                Model_Class modelClass = response.body();
//
//                                TextView newAmount = findViewById(R.id.newAmount);
//                                newAmount.setText(String.valueOf(modelClass.getNewAmount(modelClass.chosenCurrencyRate(selectedCurrency), amountEntered)));
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Model_Class>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    });
                }
            }
        });
    }
}
