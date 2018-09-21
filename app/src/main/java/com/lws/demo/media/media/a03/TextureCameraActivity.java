package com.lws.demo.media.media.a03;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;

import com.lws.demo.media.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TextureView 来预览 Camera 数据
 */
public class TextureCameraActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    @BindView(R.id.texture_view)
    TextureView textureView;

    Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_camera);
        ButterKnife.bind(this);

        textureView.setSurfaceTextureListener(this);

        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.release();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
