package com.jdbc.camerademo.view;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jdbc.camerademo.camera.ZBarCameraManager;
import com.jdbc.camerademo.decode.ZBarCaptureCallBack;
import com.jdbc.camerademo.decode.ZBarCaptureHandler;
import com.jdbc.camerademo.decode.ZBarInactivityTimer;
import com.jdbc.camerademo.decode.ZBarScanCallBack;
import com.lss.duolejie_seller.R;

public class ScanFragment extends Fragment implements SurfaceHolder.Callback,
		ZBarCaptureCallBack, ZBarScanCallBack {
	private ZBarInactivityTimer inactivityTimer;
	private ZBarCaptureHandler handler;
	private boolean flag = true;
	private boolean hasSurface = false;
	private ZBarViewfinderView viewfinderView = null;
	private ScreenOffReceiver mScreenOffReceiver = new ScreenOffReceiver();;

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			ZBarCameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new ZBarCaptureHandler(this);
		}
	}

	public ZBarViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(String result) {
		inactivityTimer.onActivity();
		CallBack(result);
	}

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_scan, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Window window = getActivity().getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		ZBarCameraManager.init(getActivity().getApplication());
		this.viewfinderView = (ZBarViewfinderView) view
				.findViewById(R.id.viewfinder_view);

		this.hasSurface = false;
		this.inactivityTimer = new ZBarInactivityTimer(getActivity());

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		getActivity().registerReceiver(this.mScreenOffReceiver, intentFilter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void CallBack(String string) {
		if (getActivity() instanceof ZBarScanCallBack)
			((ZBarScanCallBack) getActivity()).CallBack(string);
		else {
			// ToastUtil.makeToast(getActivity(),"please parent implements ZBarScanCallBack");
		}
	}

	@Override
	public void onScanStart() {
		handler = null;
		SurfaceView surfaceView = (SurfaceView) view
				.findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		inactivityTimer.onResume();
	}

	@Override
	public void onScanStop() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		ZBarCameraManager.get().offLight();
		inactivityTimer.onPause();
		ZBarCameraManager.get().closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) view
					.findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
	}

	@Override
	public void onPause() {
		onScanStop();
		super.onPause();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onResume() {
		super.onResume();
		onScanStart();
	}

	@Override
	public void onDestroy() {
		inactivityTimer.shutdown();
		getActivity().unregisterReceiver(this.mScreenOffReceiver);
		super.onDestroy();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	private class ScreenOffReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.d("CaptureActivity",
					"CaptureActivity receive screen off command ++");
			getActivity().finish();
		}

	}

	@Override
	public void setCodeResult(int result, Intent intent) {
		// TODO Auto-generated method stub

	}

}