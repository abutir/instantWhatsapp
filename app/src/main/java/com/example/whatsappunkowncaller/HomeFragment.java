package com.example.whatsappunkowncaller;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.hbb20.CountryCodePicker;

import java.math.BigInteger;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView number;
    CountryCodePicker code;
    Button chat;
    ProgressBar progressBar;

    ClipboardManager clipboardManager;
    private AdView bannerAdView;
    private InterstitialAd interstitialAd;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View r = inflater.inflate(R.layout.fragment_home, container, false);
        number = r.findViewById(R.id.number);
        copyAndPaste();
        code = r.findViewById(R.id.internationalCode);
        code.setAutoDetectedCountry(true);
        chat = r.findViewById(R.id.chatNow);
//        progressBar= r.findViewById(R.id.progress_bar_circular);
//        progressBar.setVisibility(View.INVISIBLE);

//        MobileAds.initialize(getContext(), String.valueOf(R.string.App_id));
//        bannerAdView = r.findViewById(R.id.adView);
//        final AdRequest adRequest = new AdRequest.Builder().build();
//        bannerAdView.loadAd(adRequest);

        return r;
    }

    public void copyAndPaste() {

        clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipData.Item item = clipData.getItemAt(0);

            String cliped = item.getText().toString();
            String validated ;

            String regexZ = "^[1-9][0-9]{8}";
            String regexNoZ = "\\d{10}";
            if (cliped.matches(regexZ)) {
                number.setText(cliped);
            } else if (cliped.matches(regexNoZ)){
                validated = cliped.substring(1);
                number.setText(validated);
            }
        }

    }


    public void textNow() {
        String phoneNumber , InternationalCode, fullNumber;

        phoneNumber = number.getText().toString().trim();
        InternationalCode = code.getSelectedCountryCode();

        String regex = "^[1-9][0-9]{8}";
        if (!phoneNumber.matches(regex)) {
//            progressBar.setVisibility(View.INVISIBLE);
            number.setError("ادخل رقم الهاتف بدون صفر");
            number.requestFocus();
            return;
        }
        fullNumber = InternationalCode + phoneNumber;

        String url = "https://api.whatsapp.com/send?phone=" + fullNumber;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
//        progressBar.setVisibility(View.INVISIBLE);
        getActivity().startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textNow();
//                progressBar.setVisibility(View.VISIBLE);
//
//                interstitialAd = new InterstitialAd(getContext());
//                interstitialAd.setAdUnitId(getString(R.string.Interstitial_unit_id));
//                AdRequest adRequest = new AdRequest.Builder().build();
//                interstitialAd.loadAd(adRequest);
//                interstitialAd.setAdListener(new AdListener() {
//
//                    @Override
//                    public void onAdFailedToLoad(int i) {
//                        super.onAdFailedToLoad(i);
//                        textNow();
//                    }
//
//                    @Override
//                    public void onAdClosed() {
//                        textNow();
//                    }
//
//                    public void onAdLoaded() {
//                        if (interstitialAd.isLoaded()) {
//                            interstitialAd.show();
//                            progressBar.setVisibility(View.INVISIBLE);
//
//                        }
//                    }
//                });

            }
        });

    }
}
