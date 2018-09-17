package com.lws.demo.media;

public class MainBean {
    private String mTitle;
    private Class<?> mTargetActivity;

    public MainBean(String title, Class<?> targetActivity) {
        mTitle = title;
        mTargetActivity = targetActivity;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Class<?> getTargetActivity() {
        return mTargetActivity;
    }

    public void setTargetActivity(Class<?> targetActivity) {
        mTargetActivity = targetActivity;
    }
}
