package com.lws.demo.media.opengles.b03;

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

public class SquareRenderer implements GLSurfaceView.Renderer {

    private final String vertexShaderCode = "" +
            "attribute vec4 vPosition;" +
            "uniform mat4 vMatrix;" +
            "void main(){" +
            "   gl_Position = vMatrix * vPosition;" +
            "}";

    private final String fragmentShaderCode = "" +
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main(){" +
            "   gl_FragColor = vColor;" +
            "}";

    private final float[] triangleCoords = {
            -0.5f, -0.5f, 0f,// bottom left
            0.5f, -0.5f, 0f,// bottom right
            -0.5f, 0.5f, 0f,// top left
            0.5f, 0.5f, 0f// top right
    };

    private float[] color = {1, 1, 1, 1};
    private FloatBuffer mVertexBuffer;
    private int mProgram;

    private float[] mMVPMatrix = new float[16];

    // 索引法
    private short[] index = {0, 1, 2, 1, 2, 3};
    private ShortBuffer mIndexBuffer;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 1f);

        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(triangleCoords);
        mVertexBuffer.position(0);

        ByteBuffer bb2 = ByteBuffer.allocateDirect(index.length * 2);
        bb2.order(ByteOrder.nativeOrder());
        mIndexBuffer = bb2.asShortBuffer();
        mIndexBuffer.put(index);
        mIndexBuffer.position(0);

        int vertexShader = Util.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = Util.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        float[] projectionMatrix = new float[16];
        float[] viewMatrix = new float[16];
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 7, 0, 0, 0, 0, 1, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0, 0, 0, 1);
        GLES20.glUseProgram(mProgram);

        int vMatrixHandler = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        GLES20.glUniformMatrix4fv(vMatrixHandler, 1, false, mMVPMatrix, 0);

        int positionHandler = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandler);
        GLES20.glVertexAttribPointer(positionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, mVertexBuffer);

        int colorHandler = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandler, 1, color, 0);

//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        //索引法绘制正方形
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, index.length, GLES20.GL_UNSIGNED_SHORT, mIndexBuffer);
        GLES20.glDisableVertexAttribArray(positionHandler);
    }
}
