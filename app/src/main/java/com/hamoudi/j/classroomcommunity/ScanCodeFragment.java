package com.hamoudi.j.classroomcommunity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanCodeFragment extends Fragment implements ZBarScannerView.ResultHandler {

    private ZBarScannerView scanCodeView;
    private int id = 2;

    public ScanCodeFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanCodeView.setResultHandler(this);
        scanCodeView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scanCodeView.stopCamera();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_code, container, false);
        getActivity().setTitle(getString(R.string.scanner_un_code));
        scanCodeView = new ZBarScannerView(getActivity());
        List<BarcodeFormat> barcodeFormatList = new ArrayList<>();
        barcodeFormatList.add(BarcodeFormat.QRCODE);
        scanCodeView.setFormats(barcodeFormatList);

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.scanCodeFrameLayout);
        frameLayout.addView(scanCodeView);

        return view;
    }

    public void handleResult(Result rawResult) {
        final String QRCode = rawResult.getContents();
        Toast.makeText(getActivity(), QRCode, Toast.LENGTH_SHORT).show();
        Ion.with(getActivity())
                .load(MainActivity.checkAttendingUrl + "/" + QRCode + "/" + MainActivity.ID)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> response) {
                        if(response.getHeaders().code() == 200) {
                            MainActivity.QRCode = QRCode;
                            getFragmentManager().beginTransaction().replace(R.id.frameLayout, new FriendFragment()).commit();
                        } else {
                            Toast.makeText(getActivity(), "Le serveur ne répond pas", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }




}
