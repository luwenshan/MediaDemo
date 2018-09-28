package com.lws.demo.media.opengles.b05;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.lws.demo.media.opengles.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CubeRenderer implements GLSurfaceView.Renderer {

    private final float[] cubePositions = {
            -1.0f, 1.0f, 1.0f,    //正面左上0
            -1.0f, -1.0f, 1.0f,   //正面左下1
            1.0f, -1.0f, 1.0f,    //正面右下2
            1.0f, 1.0f, 1.0f,     //正面右上3
            -1.0f, 1.0f, -1.0f,    //反面左上4
            -1.0f, -1.0f, -1.0f,   //反面左下5
            1.0f, -1.0f, -1.0f,    //反面右下6
            1.0f, 1.0f, -1.0f,     //反面右上7
    };

    private final short[] index = {
            0, 3, 2, 0, 2, 1,    //正面
            0, 1, 5, 0, 5, 4,    //左面
            0, 7, 3, 0, 4, 7,    //上面
            6, 7, 4, 6, 4, 5,    //后面
            6, 3, 7, 6, 2, 3,    //右面
            6, 5, 1, 6, 1, 2     //下面
    };

    private float[] color = {
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
    };

    private final String vertexShaderCode = "" +
            "attribute vec4 vPosition;" +
            "uniform mat4 vMatrix;" +
            "attribute vec4 aColor;" +
            "varying vec4 vColor;" +
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

    private int mProgram;
    private float[] mMVPMatrix = new float[16];

    private FloatBuffer vertexBuffer, colorBuffer;
    private ShortBuffer indexBuffer;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 1);

        ByteBuffer bb1 = ByteBuffer.allocateDirect(cubePositions.length * 4);
        bb1.order(ByteOrder.nativeOrder());
        vertexBuffer = bb1.asFloatBuffer();
        vertexBuffer.put(cubePositions);
        vertexBuffer.position(0);

        ByteBuffer bb2 = ByteBuffer.allocateDirect(color.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        colorBuffer = bb2.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);

        ByteBuffer bb3 = ByteBuffer.allocateDirect(index.length * 2);
        bb3.order(ByteOrder.nativeOrder());
        indexBuffer = bb3.asShortBuffer();
        indexBuffer.put(index);
        indexBuffer.position(0);

        int vertexShader = Util.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = Util.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        //开启深度测试
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        float[] projectionMatrix = new float[16];
        float[] viewMatrix = new float[16];
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
        Matrix.setLookAtM(viewMatrix, 0, 5, 5, 10, 0, 0, 0, 0, 1, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);

        int vertexHandler = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(vertexHandler);
        GLES20.glVertexAttribPointer(vertexHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);

        int matrixHandler = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        GLES20.glUniformMatrix4fv(matrixHandler, 1, false, mMVPMatrix, 0);

        int colorHandler = GLES20.glGetAttribLocation(mProgram, "aColor");
        GLES20.glEnableVertexAttribArray(colorHandler);
        GLES20.glVertexAttribPointer(colorHandler, 4, GLES20.GL_FLOAT, false, 0, colorBuffer);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, index.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
        GLES20.glDisableVertexAttribArray(vertexHandler);
    }
}
