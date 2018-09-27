package com.lws.demo.media.opengles.b03;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lws.demo.media.opengles.b02.TriangleRenderer;

public class SquareActivity extends AppCompatActivity {

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
