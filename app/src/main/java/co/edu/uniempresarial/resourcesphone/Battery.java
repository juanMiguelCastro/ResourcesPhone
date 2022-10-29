package co.edu.uniempresarial.resourcesphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class Battery {
    public String mensajeBattery(int currentLevel, int batteryScale){
        String mensaje="";
        if (batteryScale != 100) {
            int level = 0;
            if (currentLevel >= 0 && batteryScale > 0) {
                level = (currentLevel * 100) / batteryScale;
                mensaje=  "Battery: " + level + "%";
            }
        } else {
            mensaje= "Battery: " + currentLevel + "%";
        }
        return mensaje;
    }

}
