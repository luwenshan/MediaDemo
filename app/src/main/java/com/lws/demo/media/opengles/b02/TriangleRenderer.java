package com.lws.demo.media.opengles.b02;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.lws.demo.media.opengles.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TriangleRenderer implements GLSurfaceView.Renderer {

    private static final int COORDS_PER_VERTEX = 3;

    private final String vertexShaderCode = "" +
            "attribute vec4 vPosition;" +
            "uniform mat4 vMatrix;" +
            "varying vec4 vColor;" +
            "attribute vec4 aColor;" +
            "void main(){" +
            "   gl_Position = vMatrix * vPosition;" +
            "   vColor = aColor;" +
            "}";

    private final String fragmentShaderCode = "" +
            "precision mediump float;" +
            "varying vec4 vColor;" +
            "void main(){" +
            "   gl_FragColor = vColor;" +
            "}";

    private float[] triangleCoords = {
            0.5f, 0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节

    private float[] color = {
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };
    private FloatBuffer mColorBuffer;

    private FloatBuffer mVertexBuffer;
    private int mProgram;
    private int mPositionHandler;
    private int mColorHandler;

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1f);

        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(triangleCoords);
        mVertexBuffer.position(0);

        ByteBuffer bb2 = ByteBuffer.allocateDirect(color.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        mColorBuffer = bb2.asFloatBuffer();
        mColorBuffer.put(color);
        mColorBuffer.position(0);

        int vertexShader = Util.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = Util.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        //计算宽高比
        float ration = (float) width / height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ration, ration, -1, 1, 3, 7);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7f, 0f, 0f, 0f, 0f, 1f, 0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);

        int matrixHandler = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        GLES20.glUniformMatrix4fv(matrixHandler, 1, false, mMVPMatrix, 0);

        //获取顶点着色器的vPosition成员句柄
        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexBuffer);

        mColorHandler = GLES20.glGetAttribLocation(mProgram, "aColor");
        GLES20.glEnableVertexAttribArray(mColorHandler);
        GLES20.glVertexAttribPointer(mColorHandler, 4, GLES20.GL_FLOAT, false, 0, mColorBuffer);
//        GLES20.glUniform4fv(mColorHandler, 1, color, 0);

        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandler);
    }
}
