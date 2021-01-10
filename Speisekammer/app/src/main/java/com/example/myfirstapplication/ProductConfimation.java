package com.example.myfirstapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ProductConfimation extends Fragment {

    private String barcode;
    private String genericProductName;
    private TextView barcodeText;
    private EditText genericProductNameText;
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
        barcodeText = view.findViewById(R.id.barcodeTextConfirmation);
        genericProductNameText = view.findViewById(R.id.genericProductNameField);
        ProductConfimationArgs args = ProductConfimationArgs.fromBundle(getArguments());
        barcode = args.getBarcode();
        genericProductName = args.getGenericProductName();

        barcodeText.setText(barcode);
        genericProductNameText.setText(genericProductName);

    }
}