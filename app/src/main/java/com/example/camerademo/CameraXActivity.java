package com.example.camerademo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;

import android.annotation.SuppressLint;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraXActivity extends AppCompatActivity{
    private TextureView textureView;
    private Button btnCapture;
    private Preview preview;
    private ImageCaptureConfig imageCaptureConfig;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);
        textureView = findViewById(R.id.textureView);
        btnCapture = findViewById(R.id.captureX);


        textureView.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                startCamera();
            }
        });




        textureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                updateTransform();
            }
        });


    }

    private ExecutorService executors = Executors.newSingleThreadExecutor();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startCamera(){
        PreviewConfig previewConfig = new PreviewConfig.Builder().setTargetResolution(new Size(640, 480)).build();
        preview = new Preview(previewConfig);

        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(@NonNull Preview.PreviewOutput output) {
                ViewGroup viewGroup = (ViewGroup) textureView.getParent();
                viewGroup.removeView(textureView);
                viewGroup.addView(textureView, 0);
                textureView.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();

            }
        });

        imageCaptureConfig = new ImageCaptureConfig.Builder()
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .build();

        imageCapture = new ImageCapture(imageCaptureConfig);
//        bindCameraUseCases();
        File file = new File(CameraUtil.FOLDER + "/" + System.currentTimeMillis() + "_CameraX.jpg");
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCapture.takePicture(file, executors, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        textureView.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CameraXActivity.this, "Save success " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                        textureView.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("TAG", "error " + imageCaptureError.toString());
                                Toast.makeText(CameraXActivity.this, "Save error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });

        CameraX.bindToLifecycle(this, preview, imageCapture);

//        CameraX.bindToLifecycle(this, preview);

    }

    private void updateTransform(){
        Matrix matrix = new Matrix();
        float centerX = textureView.getWidth() / 2;
        float centerY = textureView.getHeight() / 2;
        int rotation = 0;
        switch (textureView.getDisplay().getRotation()){
            case Surface.ROTATION_0: rotation = 0;
//            case Surface.ROTATION_90: rotation = 90;
//            case Surface.ROTATION_180: rotation = 180;
//            case Surface.ROTATION_270: rotation = 270;
        }
        matrix.postRotate(-rotation, centerX, centerY);
        textureView.setTransform(matrix);
    }


}
