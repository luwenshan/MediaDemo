package com.lws.demo.media;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lws.demo.media.media.a01.DrawImageActivity;
import com.lws.demo.media.media.a02.AudioRecordActivity;
import com.lws.demo.media.media.a03.SurfaceCameraActivity;
import com.lws.demo.media.media.a03.TextureCameraActivity;
import com.lws.demo.media.media.a04.H264RecordActivity;
import com.lws.demo.media.media.a05.MediaRecordActivity;
import com.lws.demo.media.opengles.b01.OpenGLES20Activity;
import com.lws.demo.media.opengles.b02.OpenGLTriangleActivity;
import com.lws.demo.media.opengles.b03.SquareActivity;
import com.lws.demo.media.opengles.b04.CircleActivity;
import com.lws.demo.media.opengles.b05.CubeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MainAdapter mAdapter;
    private List<MainBean> mDataList;

    private String[] mPermissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private List<String> mPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDataList = getDataList();
        mAdapter = new MainAdapter(mDataList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MainBean bean = mDataList.get(position);
                startActivity(new Intent(MainActivity.this, bean.getTargetActivity()));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkPermissions();
    }

    private List<MainBean> getDataList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("SurfaceView绘制图片", DrawImageActivity.class));
        list.add(new MainBean("使用 AudioRecord 采集音频PCM并保存到文件", AudioRecordActivity.class));
        list.add(new MainBean("使用 SurfaceView 来预览 Camera 数据", SurfaceCameraActivity.class));
        list.add(new MainBean("使用 TextureView 来预览 Camera 数据", TextureCameraActivity.class));
        list.add(new MainBean("收集Camera数据，并转码为H264存储到文件", H264RecordActivity.class));
        list.add(new MainBean("音视频采集 + 混合(未完成)", MediaRecordActivity.class));
        list.add(new MainBean("OpenGL ES 2.0", OpenGLES20Activity.class));
        list.add(new MainBean("OpenGL ES 2.0 绘制等腰直角三角形", OpenGLTriangleActivity.class));
        list.add(new MainBean("OpenGL ES 2.0 绘制正方形", SquareActivity.class));
        list.add(new MainBean("OpenGL ES 2.0 绘制圆形", CircleActivity.class));
        list.add(new MainBean("OpenGL ES 2.0 绘制立方体", CubeActivity.class));
        return list;
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String mPermission : mPermissions) {
                if (ContextCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(mPermission);
                }
            }
            if (!mPermissionList.isEmpty()) {
                ActivityCompat.requestPermissions(this, mPermissionList.toArray(new String[0]), 12);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 12) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 权限被禁止！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
