package com.jdbc.camerademo.decode;

import android.content.Intent;
import android.os.Handler;

import com.jdbc.camerademo.view.ZBarViewfinderView;


public interface ZBarCaptureCallBack {

	Handler getHandler();

	ZBarViewfinderView getViewfinderView();

	void drawViewfinder();

	void setCodeResult(int result, Intent intent);

	void handleDecode(String code);

}
