package com.lws.demo.media.opengles.b04;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CircleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView GLSurfaceView = new GLSurfaceView(this);
        setContentView(GLSurfaceView);

        GLSurfaceView.setEGLContextClientVersion(2);
        GLSurfaceView.Renderer renderer = new CircleRenderer();
        GLSurfaceView.setRenderer(renderer);
//        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
