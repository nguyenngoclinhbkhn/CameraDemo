package com.example.camerademo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.camerakit.CameraKit;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnCameraX;
    private Button btnCameraView;
    private Button btnCameraKit;
    private Button btnCameraFotoapprat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCameraX = findViewById(R.id.cameraX);
        btnCameraView = findViewById(R.id.cameraView);
        btnCameraKit = findViewById(R.id.cameraKit);
        btnCameraFotoapprat = findViewById(R.id.fotoapprate);
        requestPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                1);


        btnCameraX.setOnClickListener(this);
        btnCameraView.setOnClickListener(this);
        btnCameraKit.setOnClickListener(this);
        btnCameraFotoapprat.setOnClickListener(this);
    }

    private void requestPermission(String[] permission, int requestCode) {
        if ((ActivityCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_GRANTED) &&
                ((ActivityCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_GRANTED)) &&
                ((ActivityCompat.checkSelfPermission(this, permission[2]) == PackageManager.PERMISSION_GRANTED)) &&
                (requestCode == 1)) {
            File sumFile = new File(Environment.getExternalStorageDirectory(), "CameraDemo");
            Log.e("TAG", "path " + sumFile.getAbsolutePath());
            if (!sumFile.exists()) {
                sumFile.mkdir();
            }
        } else {
            // ==> gui yeu cau de nguoi dung cho phep
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission[0], permission[1], permission[2]},
                    requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    File sumFile = new File(Environment.getExternalStorageDirectory(), "CameraDemo");
                    Log.e("TAG", "path " + sumFile.getAbsolutePath());
                    if (!sumFile.exists()) {
                        sumFile.mkdir();
                    }
                } else {
                    Toast.makeText(this, "Need acess external and camera to use camera and gallery", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cameraX: {
                startActivity(new Intent(this, CameraXActivity.class));

            }
            break;
            case R.id.cameraView: {
                startActivity(new Intent(this, CameraViewActivity.class));

            }
            break;
            case R.id.cameraKit: {
                startActivity(new Intent(this, CameraKitActivity.class));
            }
            break;
            case R.id.fotoapprate: {
                startActivity(new Intent(this, CameraFotoapparatActivity.class));
            }
            break;
        }
    }
}
