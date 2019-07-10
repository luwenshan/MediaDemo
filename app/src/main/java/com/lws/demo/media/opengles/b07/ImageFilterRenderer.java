package com.lws.demo.media.opengles.b07;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.lws.demo.media.R;
import com.lws.demo.media.opengles.Util;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ImageFilterRenderer implements GLSurfaceView.Renderer {

    private final float[] sPos = {
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f
    };

    private final float[] sCoord = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
    };

    private Context mContext;
    private int mProgram;
    private Bitmap mBitmap;

    private FloatBuffer posBuffer, coordBuffer;

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    private int mHPosition, mHCoordinate, mHMatrix, mHTexture;

    public ImageFilterRenderer(Context context) {
        mContext = context;
        posBuffer = Util.getFloatBuffer(sPos);
        coordBuffer = Util.getFloatBuffer(sCoord);

        Resources resources = mContext.getResources();
        mProgram = Util.createProgram(
                Util.loadFromAssetsFile("image_filter/vertex_image_filter", resources),
                Util.loadFromAssetsFile("image_filter/fragment_image_filter", resources));

        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.test);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1, 1, 1, 1);
        mHPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mHCoordinate = GLES20.glGetAttribLocation(mProgram, "vCoordinate");
        mHMatrix = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        mHTexture = GLES20.glGetUniformLocation(mProgram, "vTexture");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float[] projectMatrix = new float[16];
        float[] viewMatrix = new float[16];
        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        float sWH = w / (float) h;
        float sWidthHeight = width / (float) height;
        if (width > height) {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(projectMatrix, 0, -sWidthHeight * sWH, sWidthHeight * sWH, -1, 1, 3, 7);
            } else {
                Matrix.orthoM(projectMatrix, 0, -sWidthHeight / sWH, sWidthHeight / sWH, -1, 1, 3, 7);
            }
        } else {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(projectMatrix, 0, -1, 1, -1 / sWidthHeight * sWH, 1 / sWidthHeight * sWH, 3, 7);
            } else {
                Matrix.orthoM(projectMatrix, 0, -1, 1, -sWH / sWidthHeight, sWH / sWidthHeight, 3, 7);
            }
        }

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 7f, 0, 0, 0, 0, 1, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, projectMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
