package com.example.currencyconverter;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyConverterFragment extends Fragment {
    public static API_SERVICES api_services;
    private ProgressDialog progressDialog;
    private float amountEntered;
    private TextView fromText;
    private DatabaseManager databaseManager;
    private TextView toText;
    private ArrayList<String> currencyName;
    private ArrayList<String> currencySymbol;
    private View view;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.search_currency);
        menuItem.setVisible(false);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_currency_convert, container, false);
        setHasOptionsMenu(true);
        databaseManager = new DatabaseManager(getContext());

        currencyName = new ArrayList<>();
        currencySymbol = new ArrayList<>();
        loadCurrencies();

        Button convertbtn = view.findViewById(R.id.convertBtn);
        convertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar("Converting", "Please wait..");
                progressDialog.show();
                EditText amount_entered = view.findViewById(R.id.amount_entered);
                String amount = amount_entered.getText().toString();

                if(amount.trim().equals("")){
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Enter amount to convert", Toast.LENGTH_SHORT).show();
                }else if(fromText.getText().toString().equals(toText.getText().toString())){
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Check your currencies selection", Toast.LENGTH_LONG).show();
                }else {
                    amountEntered = Float.valueOf(amount); //convert amount to float
                    convertAmount(amountEntered); //convert amount entered
                }
            }
        });
        return view;
    }

    private void progressbar(String title, String message){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
    }

    private void loadCurrencies(){
        progressbar("Getting currencies", "Please wait a minute...");
        progressDialog.show();
        currencyName = new ArrayList<>();
        currencySymbol = new ArrayList<>();
        final ArrayList<Currencies> currencies = databaseManager.getAllCurrencies();
        for (int i = 0; i<currencies.size(); i++) {
            currencyName.add(currencies.get(i).getCurrencyName());
            currencySymbol.add(currencies.get(i).getCurrencyCode());
        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, currencyName);
        fromText = view.findViewById(R.id.fromText);
        toText = view.findViewById(R.id.toText);
        progressDialog.dismiss();
        Spinner fromCurrencySpinner = view.findViewById(R.id.fromSpinner);
        fromCurrencySpinner.setAdapter(adapter);
        fromCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    fromText.setText(currencySymbol.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        Spinner toCurrencySpinner = view.findViewById(R.id.toSpinner);
        toCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toText.setText(currencySymbol.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void convertAmount(float amountEntered){
        progressDialog.dismiss();
        String from = fromText.getText().toString();
        String to = toText.getText().toString();
        api_services = API_CLIENT.getConverterApiClient().create(API_SERVICES.class);
        Call<JsonObject> apiCall = api_services.currencyConvert(API_CLIENT.getConverterApiKey(), from, to,amountEntered);
        apiCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body().get("success").getAsBoolean()){
                    double result = response.body().get("result").getAsFloat();
                    result = Math.round(result * 100.0) / 100.0;;
//                    String time = response.body().getAsJsonObject("info").get("server_time").getAsString();
                    TextView newAmount = view.findViewById(R.id.newAmount);
                    newAmount.setText(String.valueOf(result));
//                    TextView serverTime = findViewById(R.id.server_timeText);
//                    serverTime.setVisibility(View.VISIBLE);
//                    serverTime.setText("As at: "+time);
                    //Toast.makeText(getApplicationContext(), total, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
