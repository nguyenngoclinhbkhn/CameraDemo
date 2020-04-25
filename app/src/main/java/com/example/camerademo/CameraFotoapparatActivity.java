package com.example.camerademo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;
import kotlin.Unit;

public class CameraFotoapparatActivity extends AppCompatActivity {
    private CameraView cameraView;
    private Button btnCapture;
    private Fotoapparat fotoapparat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_fotoapparat);
        cameraView = findViewById(R.id.cameraFoto);
        btnCapture = findViewById(R.id.captureFoto);
        fotoapparat = new Fotoapparat(CameraFotoapparatActivity.this, cameraView);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(CameraUtil.FOLDER + "/"  + System.currentTimeMillis() + "_Foto.jpg");
                fotoapparat.takePicture().saveToFile(file)
                .whenDone(new WhenDoneListener<Unit>() {
                    @Override
                    public void whenDone(@Nullable Unit unit) {
                        Toast.makeText(CameraFotoapparatActivity.this, "Save success "+ file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        fotoapparat.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fotoapparat.stop();
    }
}
