package com.lws.demo.media.a05;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;

import com.lws.demo.media.R;

public class MediaRecordActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_record);
    }

    /*****  Camera.PreviewCallback  ****/
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }

    /*****  SurfaceHolder.Callback  ****/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
