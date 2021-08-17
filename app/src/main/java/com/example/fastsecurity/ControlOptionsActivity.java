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

import com.example.fastsecurity.Util.Helper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class ControlOptionsActivity extends AppCompatActivity implements Serializable {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Helper helper;

    private BluetoothSocket bluetoothSocket = null;
    private BluetoothDevice bluetoothDevice;
    private BluetoothAdapter bluetoothAdapter = null;
    public Set<BluetoothDevice> bluetoothDeviceSet;
    static final UUID blueID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public String address1;
    public String addressName;


    private TextView cancelMotion, cancelFlame, cancelSmoke;
    private TextView activateMotion, activateFlame, activateSmoke;
    private TextView temp, getAddress, defaultOption, connectDevice, notification;
    private String hello;
    private Intent intent;

    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_options);
        helper = new Helper(getApplicationContext());
        auth = FirebaseAuth.getInstance();

        intent = getIntent();
        cancelMotion = this.findViewById(R.id.textView4);
        activateMotion = this.findViewById(R.id.textView04);
        cancelFlame = this.findViewById(R.id.textView5);
        activateFlame = this.findViewById(R.id.textView05);
        cancelSmoke = this.findViewById(R.id.textView6);
        activateSmoke = this.findViewById(R.id.textView06);
        temp = this.findViewById(R.id.textView7);
        getAddress = this.findViewById(R.id.textView9);
        defaultOption = this.findViewById(R.id.textView8);
        notification = this.findViewById(R.id.textView13);
        connectDevice = this.findViewById(R.id.textView12);

        connectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBluetoothConnection();
            }
        });
        defaultOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(5, defaultOption, defaultOption);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        cancelMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(1, activateMotion, cancelMotion);
            }
        });
        activateMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(10, cancelMotion, activateMotion);
            }
        });

        cancelFlame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(2, activateFlame, cancelFlame);
            }
        });
        activateFlame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(20, cancelFlame, activateFlame);
            }
        });

        cancelSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(3, activateSmoke, cancelSmoke);
            }
        });
        activateSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(30, cancelSmoke, activateSmoke);
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(4, temp, temp);
                if(bluetoothSocket!= null && bluetoothSocket.isConnected()){
                    try {
                        int value =  bluetoothSocket.getInputStream().read();
                        intent = new Intent(getApplicationContext(), NotificationActivity.class);
                        intent.putExtra("name", "Temperature");
                        intent.putExtra("value", String.valueOf(value));
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), NotificationActivity.class);
                intent.putExtra("name", addressName);
                intent.putExtra("value", address1);
                startActivity(intent);
            }
        });
    }

    private void sendData(int i, TextView activate, TextView cancel) {
        try {
            if(bluetoothSocket != null && bluetoothSocket.isConnected()){
                bluetoothSocket.getOutputStream().write(i);

                cancel.setVisibility(View.GONE);
                activate.setVisibility(View.VISIBLE);

                if(i == 5){
                    cancelMotion.setVisibility(View.VISIBLE);
                    cancelFlame.setVisibility(View.VISIBLE);
                    cancelSmoke.setVisibility(View.VISIBLE);
                    activateMotion.setVisibility(View.GONE);
                    activateFlame.setVisibility(View.GONE);
                    activateSmoke.setVisibility(View.GONE);
                }
                Toast.makeText(getApplicationContext(), "Data sent to " + hello, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Data not sent to " + hello, Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Log.d("ERROR SIGNAL02", e.getMessage());
            Toast.makeText(getApplicationContext(), "ERROR SIGNAL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HardwareIds")
    private void bluetoothConnectDevice() throws IOException{
        try{
            helper.progressDialogStart("Please wait", "Connecting to bluetooth device");
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            address1 = bluetoothAdapter.getAddress();
            bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();
            helper.toastMessage("bluetooth address: "+ address1 +"\n"+ "bluetooth name: "+ bluetoothAdapter.getName());
            if(bluetoothDeviceSet.size() > 0){
                for(BluetoothDevice bDevice : bluetoothDeviceSet){
                    address1 = bDevice.getAddress();
                    addressName = bDevice.getName();
                }
            }else {
                helper.progressDialogEnd();
                Toast.makeText(getApplicationContext(), "Connected to no device but ON, Pls connect to a device", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            helper.progressDialogEnd();
            Log.d("BLUETOOTH ERROR MESSAGE", "BLUETOOTH ERROR MESSAGE: " + e.getMessage());
            Toast.makeText(getApplicationContext(), "BLUETOOTH ERROR MESSAGE1", Toast.LENGTH_LONG).show();
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(address1); // connects to the device address
        bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(blueID);// create a RFCOMM (SPP) connection
        System.out.println(bluetoothSocket);
        bluetoothSocket.connect();
        helper.progressDialogEnd();
        try {
            Toast.makeText(getApplicationContext(), "Connected to device with \n Name: " + addressName+ " and with Address: " + address1, Toast.LENGTH_LONG).show();
        }catch (Exception e){}
    }

    private void checkBluetoothConnection() {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter.isEnabled()){
                if(bluetoothSocket == null || !bluetoothSocket.isConnected()){
                    bluetoothConnectDevice();
                    hello = bluetoothDevice.getName();
                    if(bluetoothSocket != null && bluetoothSocket.isConnected()){
                        Toast.makeText(getApplicationContext(), "Bluetooth Connected to " + hello, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Bluetooth not connected to any device ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    bluetoothSocket.close();
                    Toast.makeText(getApplicationContext(), "Bluetooth connection closed ", Toast.LENGTH_SHORT).show();
                }
            }else{
                helper.toastMessage("Please, Enable Bluetooth Connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR SIGNAL", e.getMessage());
            Toast.makeText(getApplicationContext(), "ERROR SIGNAL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /*try {

        }catch (Exception e){
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = auth.getCurrentUser();
        if(user == null){
            helper.gotoLoginActivity();
        }
    }
}
