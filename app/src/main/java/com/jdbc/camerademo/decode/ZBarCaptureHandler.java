package com.jdbc.camerademo.decode;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jdbc.camerademo.camera.ZBarCameraManager;
import com.lss.duolejie_seller.R;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture.
 */
public final class ZBarCaptureHandler extends Handler {

	private static final String TAG = ZBarCaptureHandler.class.getSimpleName();

	private final ZBarCaptureCallBack callBack;
	private final ZBarDecodeThread decodeThread;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public ZBarCaptureHandler(ZBarCaptureCallBack callBack) {
		this.callBack = callBack;
		decodeThread = new ZBarDecodeThread(callBack);
		decodeThread.start();
		state = State.SUCCESS;
		ZBarCameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		if (message.what == R.id.auto_focus) {
			if (state == State.PREVIEW) {
				ZBarCameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			}

		} else if (message.what == R.id.restart_preview) {
			Log.d(TAG, "Got restart preview message");
			restartPreviewAndDecode();

		} else if (message.what == R.id.decode_succeeded) {
			String strResult = (String) message.obj;
			Log.d(TAG, "Got decode succeeded message:" + strResult);
			state = State.SUCCESS;
			callBack.handleDecode(strResult);
			/***********************************************************************/

		} else if (message.what == R.id.decode_failed) {
			state = State.PREVIEW;
			ZBarCameraManager.get().requestPreviewFrame(
					decodeThread.getHandler(), R.id.decode);

		} else if (message.what == R.id.return_scan_result) {
			Log.d(TAG, "Got return scan result message");
			callBack.setCodeResult(Activity.RESULT_OK, (Intent) message.obj);

		}
	}

	public void quitSynchronously() {
		state = State.DONE;
		ZBarCameraManager.get().stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
		quit.sendToTarget();
		try {
			decodeThread.join();
		} catch (InterruptedException e) {
			// continue
		}
		removeMessages(R.id.decode_succeeded);
		removeMessages(R.id.decode_failed);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			ZBarCameraManager.get().requestPreviewFrame(
					decodeThread.getHandler(), R.id.decode);
			ZBarCameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			callBack.drawViewfinder();
		}
	}

}
