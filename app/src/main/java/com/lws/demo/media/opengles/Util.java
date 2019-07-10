package com.lws.demo.media.opengles;

import android.content.res.Resources;
import android.opengl.GLES20;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Util {
    public static int loadShader(int type, String shaderCode) {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * 加载Assets中的文本文件
     */
    public static String loadFromAssetsFile(String name, Resources res) {
        StringBuilder result = new StringBuilder();
        try {
            InputStream is = res.getAssets().open(name);
            int ch;
            byte[] buffer = new byte[1024];
            while ((ch = is.read(buffer)) > 0) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result.toString().replaceAll("\\r\\n", "\n");
    }

    public static FloatBuffer getFloatBuffer(float[] data) {
        ByteBuffer bb = ByteBuffer.allocateDirect(data.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = bb.asFloatBuffer();
        floatBuffer.put(data);
        floatBuffer.position(0);
        return floatBuffer;
    }

    /**
     * 加载顶点着色器和片元着色器，并创建Program对象
     */
    public static int createProgram(String vertexShaderCode, String fragmentShaderCode) {
        int program = GLES20.glCreateProgram();
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        return program;
    }
}
