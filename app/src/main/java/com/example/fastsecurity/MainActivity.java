package com.example.fastsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements Serializable {

    private TextView controlOption, notifications, checkConnectivity;


    public BluetoothAdapter bluetoothAdapter = null;
    public BluetoothSocket bluetoothSocket = null;
    public BluetoothDevice bluetoothDevice;
    public Set<BluetoothDevice> bluetoothDeviceSet;
    static final UUID blueID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public String address1;
    public String addressName;
    private Intent intent;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlOption = this.findViewById(R.id.textView);
        notifications = this.findViewById(R.id.textView2);
        checkConnectivity = this.findViewById(R.id.textView3);

        controlOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), ControlOptionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), NotificationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        checkConnectivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkBluetoothConnection();
            }
        });
    }

}