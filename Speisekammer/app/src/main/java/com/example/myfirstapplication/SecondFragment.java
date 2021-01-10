package com.example.myfirstapplication;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.widget.TextView;
import android.view.SurfaceHolder;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.Detector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class SecondFragment extends Fragment {

    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ArrayList<String[]> database;
    private String barcodeData;
    private String kcalper100 = "0";
    private String foundProduct = "";
    public SecondFragment() throws FileNotFoundException {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        surfaceView = view.findViewById(R.id.surfaceView);

        try {
            initialiseDetectorsAndSources();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    private void initialiseDetectorsAndSources() throws IOException {
        database = ((Speisekammer) this.getActivity().getApplication()).getDatabase();
        barcodeDetector = new BarcodeDetector.Builder(this.getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource = new CameraSource.Builder(this.getActivity(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(SecondFragment.this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(SecondFragment.this.getActivity(), new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    for(int i = 0; i < database.size();i++){
                        if(barcodes.valueAt(0).displayValue.equals(database.get(i)[0])) {
                            foundProduct = database.get(i)[1];
                            kcalper100 = database.get(i)[3];
                            System.out.println(foundProduct);
                            SecondFragmentDirections.ActionSecondFragmentToProductConfimation action = SecondFragmentDirections.actionSecondFragmentToProductConfimation();
                            action.setBarcode(barcodes.valueAt(0).displayValue);
                            action.setGenericProductName(foundProduct);
                            NavHostFragment.findNavController(SecondFragment.this)
                                    .navigate(action);
                            NavHostFragment.findNavController(SecondFragment.this)
                                    .navigate(R.id.action_SecondFragment_to_productConfimation);
                        }
                    }

                }
            }
        });
    }
}