package com.lws.demo.media.opengles.b01;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lws.demo.media.R;

public class OpenGLES20Activity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gles20);

        mGLSurfaceView = new MyGLSurfaceView(this);
        setContentView(mGLSurfaceView);

        //获取当前手机OpenGL版本的方法
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        String version = Integer.toHexString(info.reqGlEsVersion);

        //假如是opengles 1.1 info.reqGlEsVersion= 0x00010001
        //假如是opengles 2.0 info.reqGlEsVersion=  0x00020000
        Toast.makeText(this, "OpenGL ES Version: " + version, Toast.LENGTH_SHORT).show();
    }
}
