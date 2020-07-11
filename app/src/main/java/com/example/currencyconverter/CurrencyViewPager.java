package com.example.currencyconverter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CurrencyViewPager extends FragmentStatePagerAdapter {
    private int numOfTab;
    private String[] tabTitles = {"currency converter", "currency info"};

    public CurrencyViewPager(FragmentManager fm, int numOfTab) {
        super(fm);
        this.numOfTab = numOfTab;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new CurrencyConverterFragment();
            case 1:
                return new CurrencyInfoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTab;
    }
}
