package co.edu.uniempresarial.resourcesphone;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Bluetooth {
    private BluetoothAdapter btAdapter;
    private boolean flag = false;
    private Context context;
    private Activity activity;

    public Bluetooth(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }
    //1st step with bluetooth handling
    public boolean statusPermissionBT() {
        int responseBT = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if (responseBT == PackageManager.PERMISSION_GRANTED) return true;
        return false;
    }

    //2nd step with bluetooth handling
    public void getPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, Manifest.permission.BLUETOOTH)) {
            Toast.makeText(context, "Lacks permissions", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.BLUETOOTH}, 100);
        }
    }

    //complement bluetooth
    public void flagMethod() {
        if (!this.flag) {
            btAdapter = BluetoothAdapter.getDefaultAdapter();
            this.flag = true;
        }
    }

    //3st step with bluetooth handling
    @SuppressLint("MissingPermission")
    public void enableBluetooth() {
        flagMethod();
        if (btAdapter == null) {
            Toast.makeText(context, "Device does not have Bluetooth", Toast.LENGTH_LONG).show();
            btAdapter.disable();
        }

        if (!this.btAdapter.isEnabled()) {
            if (!statusPermissionBT()) {
                getPermission();
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(intent, 101);
            } else {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(intent,101);
            }
        } else {
            Toast.makeText(context, "Is enabled", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    public void disableBluetooth() {
        flagMethod();

        if (btAdapter.isEnabled()) {
            btAdapter.disable();
            Toast.makeText(context, "Turning off", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Turning on", Toast.LENGTH_SHORT).show();
        }
    }
}
