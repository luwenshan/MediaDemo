package com.lws.demo.media.a03;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lws.demo.media.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SurfaceView 来预览 Camera 数据
 */
public class SurfaceCameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    @BindView(R.id.surface_view)
    SurfaceView surfaceView;

    Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        surfaceView.getHolder().addCallback(this);

        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);

        // 取到 NV21 的数据回调
        Camera.Parameters parameters = mCamera.getParameters();
        // 配置数据回调的格式
        parameters.setPreviewFormat(ImageFormat.NV21);
        mCamera.setParameters(parameters);
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {

            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }
}
