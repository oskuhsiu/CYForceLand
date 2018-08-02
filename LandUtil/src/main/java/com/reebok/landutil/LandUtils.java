package com.reebok.landutil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Osku on 2018/8/2.
 */

public class LandUtils
{
	public static final int REQ_WRITE_SETTINGS = 1101;
	public static final int REQ_DRAW_OVERLAY = 1102;

	public static final boolean checkWriteSettingsPermission(Context context)
	{
		if (context == null)
			return false;
		return Settings.System.canWrite(context);
	}

	public static final boolean checkDrawOverlayPermisson(Context context)
	{
		if (context == null)
			return false;
		return Settings.canDrawOverlays(context);
	}

	public static final Intent preparePermissionIntent(Context context, int req)
	{
		if (req == REQ_WRITE_SETTINGS)
			return new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
											 Uri.parse("package:" + context.getPackageName()));
		else if (req == REQ_DRAW_OVERLAY)
			return new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
											 Uri.parse("package:" + context.getPackageName()));
		return null;
	}

	public static final void forceOrientation(Context context, int activityInfoOrientation)
	{
		View orientationChanger = new LinearLayout(context);
		WindowManager.LayoutParams orientationLayout =
				  new WindowManager.LayoutParams(
																	 WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
																	 0,
																	 PixelFormat.RGBA_8888);
		orientationLayout.screenOrientation = activityInfoOrientation;

		WindowManager wm = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
		wm.addView(orientationChanger, orientationLayout);
		orientationChanger.setVisibility(View.VISIBLE);
	}
}
