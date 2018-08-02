package com.jdbc.camerademo.decode;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.lss.duolejie_seller.R;

final class ZBarDecodeHandler extends Handler {

	private final ZBarCaptureCallBack callBack;
	private ImageScanner scanner;

	static {
		System.loadLibrary("iconv");
	}

	public ZBarDecodeHandler(ZBarCaptureCallBack callBack) {
		this.callBack = callBack;
		scanner = new ImageScanner();
		scanner.setConfig(0, Config.X_DENSITY, 3);
		scanner.setConfig(0, Config.Y_DENSITY, 3);

	}

	@Override
	public void handleMessage(Message message) {
		if (message.what == R.id.decode) {
			decode((byte[]) message.obj, message.arg1, message.arg2);

		} else if (message.what == R.id.quit) {
			Looper.myLooper().quit();

		}
	}

	/**
	 * Decode the data within the viewfinder rectangle, and time how long it
	 * took. For efficiency, reuse the same reader objects from one decode to
	 * the next.
	 *
	 * @param data
	 *            The preview frame.
	 * @param width
	 *            The width of the preview frame.
	 * @param height
	 *            The height of the preview frame.
	 */
	private void decode(byte[] data, int width, int height) {

		net.sourceforge.zbar.Image barcode = new net.sourceforge.zbar.Image(
				width, height, "Y800");
		Rect scanImageRect = callBack.getViewfinderView().getScanImageRect(
				height, width);
		barcode.setCrop(scanImageRect.top, scanImageRect.left,
				scanImageRect.bottom, scanImageRect.right);
		barcode.setData(data);

		int result = scanner.scanImage(barcode);
		String strResult = "";
		if (result != 0) {
			SymbolSet syms = scanner.getResults();
			for (Symbol sym : syms) {
				strResult = sym.getData().trim();
				if (!strResult.isEmpty()) {
					break;
				}
			}
		}

		if (!strResult.isEmpty()) {
			Message message = Message.obtain(callBack.getHandler(),
					R.id.decode_succeeded, strResult);
			message.sendToTarget();
		} else {
			Message message = Message.obtain(callBack.getHandler(),
					R.id.decode_failed);
			message.sendToTarget();
		}
	}

}
