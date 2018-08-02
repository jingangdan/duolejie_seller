package com.jdbc.camerademo.decode;

/**
 * Created by G on 2015/12/7.
 */
public interface ZBarScanCallBack {

    void CallBack(String string);

    void onScanStart();

    void onScanStop();
}
