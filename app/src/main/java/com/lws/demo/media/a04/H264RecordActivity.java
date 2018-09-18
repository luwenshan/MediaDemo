package com.lws.demo.media.a04;

import android.annotation.SuppressLint;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.lws.demo.media.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class H264RecordActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    @BindView(R.id.surface_view)
    SurfaceView surfaceView;

    Camera mCamera;

    H264Encoder mEncoder;

    int width = 1280;
    int height = 720;
    int framerate = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h264_record);
        ButterKnife.bind(this);

        if (!isSupportH264Codec()) {
            Toast.makeText(this, "不支持H264格式", Toast.LENGTH_SHORT).show();
        }

        surfaceView.getHolder().addCallback(this);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (mEncoder != null) {
            mEncoder.putData(data);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewFormat(ImageFormat.NV21);
        parameters.setPreviewSize(1280, 720);
        mCamera.setParameters(parameters);
        mCamera.setPreviewCallback(this);
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mEncoder = new H264Encoder(width, height, framerate);
        mEncoder.startEncoder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera = null;
        }
        if (mEncoder != null) {
            mEncoder.stopEncoder();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private boolean isSupportH264Codec() {
        // 遍历支持的编码格式信息
        if (Build.VERSION.SDK_INT >= 18) {
            for (int j = MediaCodecList.getCodecCount() - 1; j >= 0; j--) {
                MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(j);
                String[] supportedTypes = codecInfo.getSupportedTypes();
                for (int i = 0; i < supportedTypes.length; i++) {
                    if (supportedTypes[i].equalsIgnoreCase("video/avc")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
