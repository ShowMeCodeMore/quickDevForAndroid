package com.sa.all_cui.mix_core.utils.rxtool.scaner;

import android.os.Handler;
import android.os.Looper;

import com.sa.all_cui.mix_core.utils.rxtool.ActivityScanerCode;

import java.util.concurrent.CountDownLatch;

/**
 *
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

    private final CountDownLatch handlerInitLatch;
    ActivityScanerCode activity;
    private Handler handler;

	DecodeThread(ActivityScanerCode activity) {
		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
