package com.jdbc.camerademo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.lss.duolejie_seller.R;

public final class ZBarViewfinderView extends View {

	private static final long ANIMATION_DELAY = 10L;
	private static final int OPAQUE = 0xFF;

	private int CORNER_HEIGHT;
	private static final int CORNER_WIDTH = 5;
	// private static final int MIDDLE_LINE_WIDTH = 6;
	private static final int SPEEN_DISTANCE = 5;
	private static float density;

	private int slideTop;
	private int slideBottom;
	boolean isFirst = false;

	private Bitmap resultBitmap;
	private int maskColor;
	private int resultColor;
	private int measureedWidth;
	private int measureedHeight;
	private Rect frame = new Rect();;
	private int borderColor = Color.rgb(0x00, 0xdd, 0x00);

	private Paint paint;
	private Paint paintText;

	public ZBarViewfinderView(Context context) {
		super(context);
		init(context);
	}

	public ZBarViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);

	}

	private void init(Context context) {
		density = context.getResources().getDisplayMetrics().density;
		maskColor = Color.parseColor("#60000000");
		resultColor = Color.parseColor("#b0000000");
		CORNER_HEIGHT = (int) (20 * density);
		paint = new Paint();

		this.paintText = new Paint();
		this.paintText.setAntiAlias(true);
		this.paintText.setColor(Color.WHITE);
		this.paintText.setTextSize(40.0F);
		this.paintText.setTypeface(Typeface.SANS_SERIF);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureedWidth = MeasureSpec.getSize(widthMeasureSpec);
		measureedHeight = MeasureSpec.getSize(heightMeasureSpec);
		int borderWidth = (int) (measureedWidth / 3 * 2);
		int borderHeight = borderWidth;
		int left = (measureedWidth - borderWidth) / 2;
		int top = (measureedHeight - borderHeight) / 2;
		frame.set(left, top, left + borderWidth, top + borderHeight);
	}

	public Rect getScanImageRect(int w, int h) {
		Rect rect = new Rect();
		rect.left = frame.left;
		rect.right = frame.right;
		float temp = h / (float) measureedHeight;
		rect.top = (int) (frame.top * temp);
		rect.bottom = (int) (frame.bottom * temp);
		return rect;
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {

		if (frame == null) {
			return;
		}

		if (!isFirst) {
			isFirst = true;
			slideTop = frame.top;
			slideBottom = frame.bottom;
		}

		int width = canvas.getWidth();
		int height = canvas.getHeight();
		paint.setColor(resultBitmap != null ? resultColor : maskColor);

		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
				paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);

		if (resultBitmap != null) {
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {

			paint.setColor(borderColor);
			canvas.drawRect(frame.left, frame.top, frame.left + CORNER_HEIGHT,
					frame.top + CORNER_WIDTH, paint);
			canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH,
					frame.top + CORNER_HEIGHT, paint);
			canvas.drawRect(frame.right - CORNER_HEIGHT, frame.top,
					frame.right, frame.top + CORNER_WIDTH, paint);
			canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right,
					frame.top + CORNER_HEIGHT, paint);
			canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
					+ CORNER_HEIGHT, frame.bottom, paint);
			canvas.drawRect(frame.left, frame.bottom - CORNER_HEIGHT,
					frame.left + CORNER_WIDTH, frame.bottom, paint);
			canvas.drawRect(frame.right - CORNER_HEIGHT, frame.bottom
					- CORNER_WIDTH, frame.right, frame.bottom, paint);
			canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom
					- CORNER_HEIGHT, frame.right, frame.bottom, paint);

			slideTop += SPEEN_DISTANCE;
			if (slideTop >= slideBottom) {
				slideTop = frame.top;
			}
			// canvas.drawRect(frame.left +25, slideTop - MIDDLE_LINE_WIDTH/2,
			// frame.right - 25,slideTop + MIDDLE_LINE_WIDTH/2, paint);
			Rect lineRect = new Rect();
			lineRect.left = frame.left;
			lineRect.right = frame.right;
			lineRect.top = slideTop;
			lineRect.bottom = slideTop + 18;
			canvas.drawBitmap(((BitmapDrawable) (getResources()
					.getDrawable(R.drawable.scan_line))).getBitmap(), null,
					lineRect, paint);

			Rect rectText = new Rect();
			String str = "";
			this.paintText.getTextBounds(str, 0, str.length(), rectText);
			canvas.drawText(str,
					(frame.left + frame.right - rectText.width()) / 2,
					80 + frame.bottom, this.paintText);

			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
					frame.right, frame.bottom);

		}
	}

	public void drawViewfinder() {
		Bitmap resultBitmap = this.resultBitmap;
		this.resultBitmap = null;
		if (resultBitmap != null) {
			resultBitmap.recycle();
		}
		invalidate();
	}

	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

}
