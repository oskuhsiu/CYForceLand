package com.reebok.landutil;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
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

	private static boolean ENABLED = true;
	private static View orientationChanger = null;

	public static void enable()
	{
		ENABLED = true;
	}

	public static void disable()
	{
		ENABLED = false;
	}

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

	private static final void requestPermission(Activity activity, int req)
	{
		if (!ENABLED)
			return;

		if((activity.getApplicationInfo().flags & ApplicationInfo.FLAG_SYSTEM) != 0)
		{
			return;
		}

		Intent intent = null;
		if (req == REQ_WRITE_SETTINGS)
			intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
												Uri.parse("package:" + activity.getPackageName()));
		else if (req == REQ_DRAW_OVERLAY)
			intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
												Uri.parse("package:" + activity.getPackageName()));

		if (intent != null)
			activity.startActivityForResult(intent, req);
	}

	private static final void rotateIt(Context context, int activityInfoOrientation)
	{
		if (!ENABLED)
			return;

		WindowManager wm = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);

		if (orientationChanger != null)
			try
			{
				wm.removeView(orientationChanger);
			}
			catch (Exception all)
			{
				all.printStackTrace();
			}

		orientationChanger = new LinearLayout(context);
		WindowManager.LayoutParams orientationLayout =
				  new WindowManager.LayoutParams(
																	 WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
																	 0,
																	 PixelFormat.RGBA_8888);
		orientationLayout.screenOrientation = activityInfoOrientation;

		wm.addView(orientationChanger, orientationLayout);
		orientationChanger.setVisibility(View.VISIBLE);
	}

	public static final void forceOrientation(Activity activity, int activityInfoOrientation)
	{
		if (!ENABLED)
			return;

		if (!checkWriteSettingsPermission(activity))
			requestPermission(activity, REQ_WRITE_SETTINGS);
		else
		{
			if (!checkDrawOverlayPermisson(activity))
				requestPermission(activity, REQ_DRAW_OVERLAY);
			else
				rotateIt(activity, activityInfoOrientation);
		}
	}
}
