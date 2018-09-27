package com.lws.demo.media.opengles.b02;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenGLTriangleActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;

    private TriangleRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        setContentView(mGLSurfaceView);

        mGLSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new TriangleRenderer();
        mGLSurfaceView.setRenderer(mRenderer);
//        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
