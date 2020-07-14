package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CurrencyInfoFragment.onResponseListener, CurrencyConverterFragment.SendCurrencies{
    public static API_SERVICES api_services;
    private MaterialSearchView searchView;
    private ListView currencyList;
    private ProfileAdapter profileAdapter;
    private CurrencyProfileResponse currenciesProfiles;
    private ViewPager currencyViewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_currency);
        searchView.setMenuItem(menuItem);
        return true;
    }

    @Override
    public void onResponse(CurrencyProfileResponse profiles) {
        currenciesProfiles = profiles;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar); //setting custom toolbar as actionbar
        getWindow().setStatusBarColor(getColor(R.color.blueBlackColor)); //change the window's status bar's color
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainFrame, new CurrencyConverterFragment(), "currency_converter_frag").addToBackStack(null).commit();  //add the converter to mainFrame first to load

        searchViewActivate(); //activate search functionality

        TabLayout currencyTabLayout = findViewById(R.id.currencyTabLayout);
        currencyViewPager = findViewById(R.id.mainFrame);
        currencyViewPager.setAdapter(new CurrencyViewPager(getSupportFragmentManager(), currencyTabLayout.getTabCount()));
        currencyViewPager.setOffscreenPageLimit(2);
        currencyTabLayout.setupWithViewPager(currencyViewPager);
    }

    //function that activates the search functionality
    private void searchViewActivate (){
        searchView = findViewById(R.id.searchView);
        searchView.setBackgroundColor(getColor(R.color.blueBlackColor));
        searchView.setTextColor(getColor(R.color.whiteColor));
        searchView.setBackIcon(getDrawable(R.drawable.ic_arrow_back_black_24dp));
        searchView.setCloseIcon(getDrawable(R.drawable.ic_close_black_24dp));
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {}

            @Override
            public void onSearchViewClosed() { //when search box has been closed
                if (currenciesProfiles != null){
                    profileAdapter = new ProfileAdapter(MainActivity.this, currenciesProfiles.getResponse(), getSupportFragmentManager()); //default
                    setList(profileAdapter);
                }
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //when user starts typing in search box
                if(!(newText.isEmpty())){ //if the user typed text isn't empty
                    checkForSearchItem(newText); //search for text searched
                }
                return true;
            }
        });
    }

    //function to query for country or currency searched for
    private void checkForSearchItem (String newText) {
        ArrayList<CurrencyProfile> currencyProfilesFound = new ArrayList<>(); //an array to store all our matched items
        if (!(currenciesProfiles == null)){ //if the API fetch is successful
            for (CurrencyProfile currencyProfile: currenciesProfiles.getResponse()){
                if (currencyProfile.getCountry() == null){ //Some currencies don't have countries (crypto). An empty string is used instead of null
                    String country = "";
                    //checking if query contains with a country's name or crypto
                    if (currencyProfile.getName().trim().toLowerCase().contains(newText.trim().toLowerCase()) ||
                            country.trim().toLowerCase().contains(newText.trim().toLowerCase())){
                        currencyProfilesFound.add(currencyProfile); //add to matches found
                    }
                }else {
                    //checking if query contains with a country's currency or crypto
                    if (currencyProfile.getName().trim().toLowerCase().contains(newText.trim().toLowerCase()) ||
                            currencyProfile.getCountry().trim().toLowerCase().contains(newText.trim().toLowerCase())){
                        currencyProfilesFound.add(currencyProfile); //add to matches found
                    }
                }
                profileAdapter = new ProfileAdapter(MainActivity.this, currencyProfilesFound, getSupportFragmentManager()); //use new matches to set the profile adapter
                setList(profileAdapter); //reset the profile's list
            }
            TextView noResultsText = findViewById(R.id.noResultsText);
            if (currencyProfilesFound.isEmpty()){ //if queried user text doesn't match
                noResultsText.setVisibility(View.VISIBLE);
                String baseError = "No country or currency for: ";
                noResultsText.setText(baseError.concat(newText));
            }else{
                noResultsText.setVisibility(View.INVISIBLE);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Data isn't loading to search", Toast.LENGTH_SHORT).show();
        }
    }

    //function that sets the list with profile adapter
    private void setList(ProfileAdapter adapter){
        currencyList = findViewById(R.id.currency_lists);
        currencyList.setAdapter(adapter);
    }

    @Override
    public void onLoadCurrencies(ArrayList<String> currencies) {
        CurrencyViewPager ad = (CurrencyViewPager) currencyViewPager.getAdapter();
        CurrencyInfoFragment fragment = (CurrencyInfoFragment) ad.getItem(1);
        if (fragment != null) {
            fragment.receiveSymbols(currencies);
        }
    }
}
