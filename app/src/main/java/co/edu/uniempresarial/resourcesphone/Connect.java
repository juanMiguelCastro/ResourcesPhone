package co.edu.uniempresarial.resourcesphone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * @author: Hember Güiza Cardenaz
 * @author: Juan Miguel Castro Rojas
 */
public class Connect {
    //Connectivity init and end
    public String connect(Context context, ConnectivityManager connectivityManager) {
        String mensaje="";
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (isConnected) mensaje="Is connected";

        return mensaje;
    }
}
