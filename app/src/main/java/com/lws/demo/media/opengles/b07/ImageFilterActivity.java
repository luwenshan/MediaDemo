package com.lws.demo.media.opengles.b07;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.lws.demo.media.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 绘制纹理贴图
 */
public class ImageFilterActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.gl_surface_view)
    GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter);
        ButterKnife.bind(this);

        glSurfaceView.setEGLContextClientVersion(2);
        GLSurfaceView.Renderer renderer = new ImageFilterRenderer(this);
        glSurfaceView.setRenderer(renderer);
//        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @OnClick(R.id.btn1)
    public void onBtn1Clicked() {
    }

    @OnClick(R.id.btn2)
    public void onBtn2Clicked() {
    }

    @OnClick(R.id.btn3)
    public void onBtn3Clicked() {
    }

    @OnClick(R.id.btn4)
    public void onBtn4Clicked() {
    }

    @OnClick(R.id.btn5)
    public void onBtn5Clicked() {
    }
}
