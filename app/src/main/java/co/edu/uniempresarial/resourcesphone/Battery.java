package co.edu.uniempresarial.resourcesphone;

/**
 * @author: Hember Güiza Cardenaz
 * @author: Juan Miguel Castro Rojas
 */
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
