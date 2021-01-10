package com.example.myfirstapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductConfimation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductConfimation extends Fragment {

    private String barcode;
    private TextView barcodeText;

    public ProductConfimation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_confimation, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        barcodeText = view.findViewById(R.id.barcodeText);

        ProductConfimationArgs args = ProductConfimationArgs.fromBundle(getArguments());
        String barcode = args.getBarcode();
        barcodeText.post(new Runnable() {
            @Override
            public void run() {
                barcodeText.setText(barcode);
            }
        });
    }
}