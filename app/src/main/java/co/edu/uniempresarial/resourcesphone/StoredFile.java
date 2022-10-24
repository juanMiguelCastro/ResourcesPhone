package co.edu.uniempresarial.resourcesphone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;

public class StoredFile {

    public static final int PERMISSION_CODE = 100;

    private Context context;
    private Activity activity;

    public StoredFile(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public boolean statusPermission(){
        int answer = ContextCompat.checkSelfPermission
                (this.context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((answer == PackageManager.PERMISSION_GRANTED))return true;
        else return false;
    }

    public void requestPermissionWES() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                Toast.makeText(context, "Cannot have permission", Toast.LENGTH_SHORT).show();
            else {
                ActivityCompat.requestPermissions(
                        this.activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODE)
                ;
                Toast.makeText(context, "Given permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveFile(String fileName, String info) {
        File directory = null;
        requestPermissionWES();
        if (statusPermission()) {
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    directory = new File(Environment.getExternalStorageDirectory(), "thisIsATestingDirectory");
                    this.createDirectory(directory);
                    Toast.makeText(context, "Path:" + directory, Toast.LENGTH_LONG).show();
                } else {
                    directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "thisIsATestingDirectory");
                    this.createDirectory(directory);
                    Toast.makeText(context, "Path:" + directory, Toast.LENGTH_LONG).show();
                }

                if (directory != null) {
                    File file = new File(directory, fileName);
                    FileWriter writer = new FileWriter(file);
                    writer.append(info);
                    writer.flush();
                    writer.close();
                    Toast.makeText(context, "file have been saved", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(context, "file could not been saved", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No permissions", Toast.LENGTH_SHORT).show();
        }
    }

    private void createDirectory(File file) {
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
