package com.example.currencyconverter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DisplayBottomSheet extends BottomSheetDialogFragment {
    private CurrencyProfile currencyProfile;

    public void setCurrencyProfile(CurrencyProfile currencyProfile) {
        this.currencyProfile = currencyProfile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_currency_display, container, false);
        TextView currencyName = view.findViewById(R.id.currency);
        currencyName.setText(String.format("%s (%s)", currencyProfile.getName(), currencyProfile.getShortName()));
        TextView countryName = view.findViewById(R.id.countryName);
        if (currencyProfile.getCountry().equals("")){
            countryName.setText("No country");
        }else{
            countryName.setText(currencyProfile.getCountry());
        }

        TextView type = view.findViewById(R.id.typeText);
        type.setText(currencyProfile.getType());
        TextView bank = view.findViewById(R.id.bankText);
        bank.setText(currencyProfile.getBank());
        TextView bankNotes = view.findViewById(R.id.bankNotesText);
        bankNotes.setText(currencyProfile.getBankNotes());
        TextView symbols = view.findViewById(R.id.symbolText);
        symbols.setText(currencyProfile.getBankNotes2());
        TextView coins = view.findViewById(R.id.coinsText);
        coins.setText(currencyProfile.getCoins());
        TextView code = view.findViewById(R.id.codeText);
        code.setText(currencyProfile.getCodeN());
        TextView subUnit = view.findViewById(R.id.subunitText);
        subUnit.setText(currencyProfile.getSubUnit());
        TextView websiteAddress = view.findViewById(R.id.websiteText);
        websiteAddress.setText(currencyProfile.getWebsite());
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);;
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return dialog;
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight - windowHeight / 3;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
