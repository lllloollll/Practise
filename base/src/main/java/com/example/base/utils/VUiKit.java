package com.example.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;

import org.jdeferred.android.AndroidDeferredManager;


/**
 * @author Long
 *         <p>
 *         A set of tools for UI.
 */
public class VUiKit {
	private static final AndroidDeferredManager gDM = new AndroidDeferredManager();
	private static final Handler gUiHandler = new Handler(Looper.getMainLooper());

	public static AndroidDeferredManager defer() {
		return gDM;
	}

	public static int tv_save_downloadPx(Context context, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	public static void post(Runnable r) {
		gUiHandler.post(r);
	}

	public static void postDelayed(long delay, Runnable r) {
		gUiHandler.postDelayed(r, delay);
	}

	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static final int getViewBackgroud(View view){
		int color = Color.TRANSPARENT;
		Drawable background = view.getBackground();
		if (background instanceof ColorDrawable)
			color = ((ColorDrawable) background).getColor();
		return color;
	}

}
