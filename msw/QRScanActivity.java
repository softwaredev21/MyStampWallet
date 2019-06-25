package com.cowboy.msw;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QRScanActivity extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private View capture;
    private View stop;
    private Context myContext;
    private LinearLayout cameraPreview;
    public static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        //If authorisation not granted for camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        else {
            initCamera();
        }

        stop = findViewById(R.id.btnStop);
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(MainActivity.SCAN_RESULT_STOP);
                finish();
            }
        });
    }

    private void initCamera() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        myContext = this;

        mCamera =  Camera.open();
        mCamera.setDisplayOrientation(90);
        cameraPreview = (LinearLayout) findViewById(R.id.cPreview);
        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview);

        capture = findViewById(R.id.btnScan);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCamera.takePicture(null, null, mPicture);
                setResult(MainActivity.SCAN_RESULT_OK);
                finish();
            }
        });

        mCamera.startPreview();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCamera();
                } else {
                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}