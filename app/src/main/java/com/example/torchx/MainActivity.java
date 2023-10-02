package com.example.torchx;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       imageView= findViewById(R.id.torchButton);


        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera Permission is Required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }
    private  void runFlashlight(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!state){
                    CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                       String cameraId =cameraManager.getCameraIdList()[0];
                       cameraManager.setTorchMode(cameraId,true);
                       state=true;
                        imageView.setImageResource(R.drawable.on);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                        CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                        try {
                            String cameraId =cameraManager.getCameraIdList()[0];
                            cameraManager.setTorchMode(cameraId,false);
                            state=false;
                            imageView.setImageResource(R.drawable.off);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        });
    }
}