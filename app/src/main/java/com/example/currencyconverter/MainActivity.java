package com.example.currencyconverter;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    public static API_SERVICES api_services;
    private ProgressDialog progressDialog;
    private float amountEntered;
    private TextView fromText;
    private TextView toText;
    private ArrayList<String> currencyName;
    private ArrayList currencySymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyName = new ArrayList<>();
        currencySymbol = new ArrayList();
        loadCurrencies();

        Button convertbtn = findViewById(R.id.convertBtn);
        convertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar("Converting", "Please wait..");
                progressDialog.show();
                EditText amount_entered = findViewById(R.id.amount_entered);
                String amount = amount_entered.getText().toString();

                if(amount.trim().equals("")){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Enter amount to convert", Toast.LENGTH_SHORT).show();
                }else if(fromText.getText().toString().equals(toText.getText().toString())){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Cannot convert to same currency", Toast.LENGTH_SHORT).show();
                }else {
                    amountEntered = Float.valueOf(amount);
                    convertAmount(amountEntered);
                }
            }
        });
    }

    private void progressbar(String title, String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
    }

    private void loadCurrencies(){
        progressbar("Getting currencies", "Please wait a minute...");
        progressDialog.show();
        api_services = API_CLIENT.getApiClient().create(API_SERVICES.class);
        Call<JsonObject> apiCall = api_services.getCurrencies(API_CLIENT.getApiKey());
        apiCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    JsonObject symbols = response.body();
//                    Log.v("apiRequest", symbols.toString());
                    for (Map.Entry<String,JsonElement> entry : symbols.entrySet()) {
                        Log.v("apiRequest", entry.getValue().getAsString());
                        currencyName.add(entry.getValue().getAsString());
                        currencySymbol.add(entry.getKey());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, currencyName);
                    fromText = findViewById(R.id.fromText);
                    fromText.setText(currencySymbol.get(0).toString());
                    toText = findViewById(R.id.toText);
                    toText.setText(currencySymbol.get(0).toString());

                    Spinner fromCurrencySpinner = findViewById(R.id.fromSpinner);
                    fromCurrencySpinner.setAdapter(adapter);
                    fromCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            fromText.setText(currencySymbol.get(i).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });
                    Spinner toCurrencySpinner = findViewById(R.id.toSpinner);
                    toCurrencySpinner.setAdapter(adapter);
                    toCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            toText.setText(currencySymbol.get(i).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void convertAmount(float amountEntered){
        progressDialog.dismiss();
        String from = fromText.getText().toString();
        String to = toText.getText().toString();
        Call<JsonObject> apiCall = api_services.currencyConvert(API_CLIENT.getApiKey(), from, to,amountEntered);
        apiCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body().get("success").getAsBoolean()){
                    double result = response.body().get("result").getAsFloat();
                    result = Math.round(result * 100.0) / 100.0;;
//                    String time = response.body().getAsJsonObject("info").get("server_time").getAsString();
                    TextView newAmount = findViewById(R.id.newAmount);
                    newAmount.setText(result+"");
//                    TextView serverTime = findViewById(R.id.server_timeText);
//                    serverTime.setVisibility(View.VISIBLE);
//                    serverTime.setText("As at: "+time);
                    //Toast.makeText(getApplicationContext(), total, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
