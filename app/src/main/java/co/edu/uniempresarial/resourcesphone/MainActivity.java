package co.edu.uniempresarial.resourcesphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int BATTERY_NOT_GIVEN = -1;
    private Context context;
    private Activity activity;

    private TextView tvVersionAndroid;
    private TextView tvBattery;

    public IntentFilter batteryFilter;
    private BluetoothAdapter btAdapter;

    private boolean flag = false;

    private EditText fileName;
    private StoredFile file;

    private TextView tvConnection;
    public ConnectivityManager connectivityManager;

    private LocationManager locationManager = null;
    private TextView latitude;
    private TextView longitude;
    private boolean flagLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        begin();

        context = getApplicationContext();
        activity = this;

        batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryPower, batteryFilter);

        getPermission();
    }

    //battery end
    @Override
    protected void onResume() {
        super.onResume();
        String versionSO = Build.VERSION.RELEASE;
        int versionSDK = Build.VERSION.SDK_INT;
        tvVersionAndroid.setText("OS Version: " + versionSO + " SDK version: " + versionSDK);

        connect();

    }

    private void begin() {
        tvVersionAndroid = findViewById(R.id.tvVersionAndroid);
        tvBattery = findViewById(R.id.tvBattery);
        fileName = findViewById(R.id.fileName);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        tvConnection = findViewById(R.id.tvConnection);
    }

    //init
    public void saveFile(View view) {
        String name = this.fileName.getText().toString() + ".txt";
        String battery = tvBattery.getText().toString() + ".txt";
        this.file = new StoredFile(context, activity);
        this.file.saveFile(name, battery);
    }


    //battery init
    public BroadcastReceiver batteryPower = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, BATTERY_NOT_GIVEN);
            int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, BATTERY_NOT_GIVEN);

            if (batteryScale != 100) {
                int level = 0;
                if (currentLevel >= 0 && batteryScale > 0) {
                    level = (currentLevel * 100) / batteryScale;
                    tvBattery.setText("Battery: " + level + "%");
                }
            } else {
                tvBattery.setText("Battery: " + currentLevel + "%");
            }
        }
    };

    //Connectivity init and end
    private void connect() {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (isConnected) tvConnection.setText("Is connected");
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
            Toast.makeText(this, "Lacks permissions", Toast.LENGTH_SHORT).show();
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
    public void enableBluetooth(View view) {
        flagMethod();
        if (btAdapter == null) {
            Toast.makeText(context, "Device does not have Bluetooth", Toast.LENGTH_LONG).show();
            btAdapter.disable();
        }

        if (!this.btAdapter.isEnabled()) {
            if (!statusPermissionBT()) {
                getPermission();
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 101);
            } else {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 101);
            }
        } else {
            Toast.makeText(context, "Is enabled", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    public void disableBluetooth(View view) {
        flagMethod();

        if (btAdapter.isEnabled()) {
            btAdapter.disable();
            Toast.makeText(context, "Turning off", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Turning on", Toast.LENGTH_SHORT).show();
        }
    }

}