package com.example.currencyconverter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.material.snackbar.Snackbar.*;

public class CurrencyInfoFragment extends Fragment {
    private static API_SERVICES api_services;
    private DatabaseManager databaseManager;
    private MaterialSearchView searchView;
    private ProfileAdapter profileAdapter;
    private ListView currencyList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Snackbar snackbar;
    private View view;

    public interface onResponseListener{
        void onResponse(CurrencyProfileResponse profiles);
    }
    onResponseListener responseListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            responseListener = (onResponseListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+ " must implement onResponseListener");
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.search_currency);
        menuItem.setEnabled(true);
        menuItem.setVisible(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_currency_info, container, false); //inflating layout to this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setRefreshing(true); //initially set refreshLayout to refreshing mode
        snackbar = make(swipeRefreshLayout, "Internet connection not available", LENGTH_LONG)
                .setActionTextColor(getContext().getColor(R.color.EarthYellowColor)); //snackbar initialized with default message
        if (isNetworkAvailable()){ //function that checks internet availability
            loadInfo(view, getCurrencySymbols()); //fetch currency info from API based on currency symbols to this view
        }else{
            swipeRefreshLayout.setRefreshing(false);
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadInfo(view, getCurrencySymbols()); //fetch currency info from API based on currency symbols to this view
                }
            }).show(); //show snackbar with default message and action
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { //a listener to check whether user has refreshed using swipelayout
            @Override
            public void onRefresh() {
                loadInfo(view, getCurrencySymbols()); //fetch currency info from API based on currency symbols to this view
            }
        });

        constantNetworkChecks(60000); //constantly checks the internet connectivity
    }

    //function that loads currency info from the API to this current view
    private void loadInfo (final View view, final String currencySymbol){
        api_services = API_CLIENT.getInfoApiClient().create(API_SERVICES.class);
        Call<CurrencyProfileResponse> apiCall = api_services.getInfo(API_CLIENT.getSymbolType(), currencySymbol, API_CLIENT.getInfoApiKey());
        apiCall.enqueue(new Callback<CurrencyProfileResponse>() {
            @Override
            public void onResponse(Call<CurrencyProfileResponse> call, Response<CurrencyProfileResponse> response) { //if there is a response from the endpoint
                if (response.isSuccessful() && response.body().getCode() == 200){ //upon a successful request
                    CurrencyProfileResponse currencyProfile = response.body();
                    profileAdapter = new ProfileAdapter(getContext(), currencyProfile.getResponse(), getFragmentManager(), getActivity());
                    responseListener.onResponse(currencyProfile);
                    currencyList = view.findViewById(R.id.currency_lists);
                    currencyList.setAdapter(profileAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    Toast.makeText(getContext(), "not getting any data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CurrencyProfileResponse> call, Throwable t) { //if there is NO response from the endpoint
                snackbar.setText("Failed to load"); //change the default message of snackbar and show
                if (!isNetworkAvailable()){
                    swipeRefreshLayout.setRefreshing(false);
                    snackbar.setText("Check your internet connection");
                    snackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadInfo(view, getCurrencySymbols());
                            Log.v("reload", "reloaded");
                        }
                    }).show();
                }else{
                    snackbar.setText("Failed: not getting any data").show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    //function that checks internet connection
    private boolean isNetworkAvailable () {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
            connected = true;
        }else connected = false;
        return connected;
    }

    //function that gets currency symbols from db
    private String getCurrencySymbols () {
        String currencySymbol = "";
        databaseManager = new DatabaseManager(getContext());  //instantiate db manager
        final ArrayList<Currencies> currencies = databaseManager.getAllCurrencies(); //get all currencies from db manager
        for (int i = 0; i<currencies.size(); i++) { //loop through all the currencies to format the currency symbols for processing...
            if(i == 0){
                currencySymbol += currencies.get(i).getCurrencyCode();
            }else if (i == currencies.size()){
                currencySymbol += currencies.get(i).getCurrencyCode()+ ",";
            }else{
                currencySymbol += ","+currencies.get(i).getCurrencyCode();
            }
        }
        return currencySymbol; //format would be in the form {AED, GHS, EUR, THS}...
    }

    private void constantNetworkChecks(final int interval){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isNetworkAvailable()){
                        snackbar.show();
                    }
                }catch (Exception e){
                    Log.v("constantCheck", e.getMessage());
                }finally {
                    new Handler().postDelayed(this, interval);
                }
            }
        };
        runnable.run();
    }
}
