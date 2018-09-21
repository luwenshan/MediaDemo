package com.lws.demo.media.media.a01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lws.demo.media.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在SurfaceView中绘制图片
 */
public class DrawImageActivity extends AppCompatActivity {

    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_image);
        ButterKnife.bind(this);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (holder == null) return;
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setStyle(Paint.Style.STROKE);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
                Canvas canvas = holder.lockCanvas();
                canvas.drawBitmap(bitmap, 0, 0, paint);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
}
