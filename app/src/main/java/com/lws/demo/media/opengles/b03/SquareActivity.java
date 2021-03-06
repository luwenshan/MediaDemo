package com.lws.demo.media.opengles.b03;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SquareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView GLSurfaceView = new GLSurfaceView(this);
        setContentView(GLSurfaceView);

        GLSurfaceView.setEGLContextClientVersion(2);
        SquareRenderer renderer = new SquareRenderer();
        GLSurfaceView.setRenderer(renderer);
//        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
