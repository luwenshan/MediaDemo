package com.lws.demo.media.opengles.b06;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lws.demo.media.opengles.b05.CubeRenderer;

/**
 * 绘制纹理贴图
 */
public class ImageTextureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView GLSurfaceView = new GLSurfaceView(this);
        setContentView(GLSurfaceView);

        GLSurfaceView.setEGLContextClientVersion(2);
        GLSurfaceView.Renderer renderer = new ImageTextureRenderer(this);
        GLSurfaceView.setRenderer(renderer);
//        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
