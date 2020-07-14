package com.example.currencyconverter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import java.io.InputStream;
import java.util.List;

public class ProfileAdapter extends ArrayAdapter<CurrencyProfile> {
    private FragmentManager getSupportFragmentManager;

    public ProfileAdapter(@NonNull Context context, @NonNull List<CurrencyProfile> objects, FragmentManager fragmentManager) {
        super(context, 0, objects);
        this.getSupportFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_currency_info, parent, false);
        final CurrencyProfile currencyProfile = getItem(position);
        TextView currencyName = convertView.findViewById(R.id.currencyNameText);
        currencyName.setText(currencyProfile.getName());
        TextView currencySymbol = convertView.findViewById(R.id.symbolText);
        currencySymbol.setText(currencyProfile.getSymbol());
        TextView countryName = convertView.findViewById(R.id.countryNameText);
        ImageView countryIcon = convertView.findViewById(R.id.countryIcon);

        if (currencyProfile.getType().equals("forex")){
            countryName.setText(currencyProfile.getCountry());
        }else {
            getPosition(currencyProfile);
        }

        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(getContext())
                .using(Glide.buildStreamModelLoader(Uri.class, getContext()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>()).placeholder(R.drawable.ic_currency_info);

        requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(currencyProfile.getIcon()))
                .into(countryIcon);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayBottomSheet displayBottomSheet = new DisplayBottomSheet();
                displayBottomSheet.setCurrencyProfile(currencyProfile);
                displayBottomSheet.show(getSupportFragmentManager, null);
            }
        });

        return convertView;
    }
}
