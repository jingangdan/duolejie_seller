
package com.jdbc.camerademo.decode;

import java.util.concurrent.CountDownLatch;

import android.os.Handler;
import android.os.Looper;

final class ZBarDecodeThread extends Thread {

  private final ZBarCaptureCallBack callBack;
  private Handler handler;
  private final CountDownLatch handlerInitLatch;

  ZBarDecodeThread(ZBarCaptureCallBack callBack)
  {
    this.callBack = callBack;
    handlerInitLatch = new CountDownLatch(1);
  }

  Handler getHandler() {
    try 
    {
      handlerInitLatch.await();
    } catch (InterruptedException ie) {
      // continue?
    }
    return handler;
  }

  @Override
  public void run() {
    Looper.prepare();
    handler = new ZBarDecodeHandler(callBack);
    handlerInitLatch.countDown();
    Looper.loop();
  }

}
