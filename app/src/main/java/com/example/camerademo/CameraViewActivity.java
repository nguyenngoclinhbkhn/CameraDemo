package com.example.camerademo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;

import java.io.File;
import java.io.FileOutputStream;

public class CameraViewActivity extends AppCompatActivity {
    private CameraView cameraView;
    private Button btnCapture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
        cameraView = findViewById(R.id.cameraView2);
        btnCapture = findViewById(R.id.captureView);

        cameraView.setLifecycleOwner(this);

        btnCapture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                cameraView.addCameraListener(new CameraListener() {
                    @Override
                    public void onPictureTaken(@NonNull PictureResult result) {

                        // If planning to save a file on a background thread,
                        // just use toFile. Ensure you have permissions.
                        File savedPhoto = new File(CameraUtil.FOLDER + "/" + System.currentTimeMillis()+ "_CameraView.jpg");
                        result.toFile(savedPhoto, new FileCallback() {
                            @Override
                            public void onFileReady(@Nullable File file) {
                                Log.e("TAG", "ready");
                                Toast.makeText(CameraViewActivity.this, "Save success " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                            }
                        });

//                        // Access the raw data if needed.
//                        byte[] data = result.getData();
                    }
                });
                cameraView.takePicture();

            }
        });



    }
}
