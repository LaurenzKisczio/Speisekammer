package com.example.myfirstapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class InventoryFragment extends Fragment {
    private String output = "";

    public InventoryFragment() {
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

        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = this.getActivity().getPreferences(this.getContext().MODE_PRIVATE);
        TextView inventoryText = view.findViewById(R.id.InventoryList);
        view.findViewById(R.id.clearInventory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();
                inventoryText.setText("");
            }
        });

        Map<String, ?> barcodes = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : barcodes.entrySet()) {
            output += entry.getKey() + "/" + entry.getValue() + "\n";
        }
        inventoryText.setText(output);
    }
}