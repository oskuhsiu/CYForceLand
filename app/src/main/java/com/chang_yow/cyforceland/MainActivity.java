package com.chang_yow.cyforceland;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.reebok.landutil.LandUtils;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

//		if (requestCode == LandUtils.REQ_WRITE_SETTINGS || requestCode == LandUtils.REQ_DRAW_OVERLAY)
//			if (!LandUtils.checkWriteSettingsPermission(MainActivity.this) ||
//							!LandUtils.checkDrawOverlayPermisson(MainActivity.this))
//				LandUtils.disable();
	}

	@Override
	public void onStart()
	{
		super.onStart();
		LandUtils.forceOrientation(MainActivity.this, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	public void open(View view)
	{
		Intent i;
		PackageManager pm = getPackageManager();
		try
		{
			i = pm.getLaunchIntentForPackage("com.spotify.music");
			if (i == null)
				throw new PackageManager.NameNotFoundException();
			i.addCategory(Intent.CATEGORY_LAUNCHER);
			startActivity(i);
		}
		catch (PackageManager.NameNotFoundException e)
		{

		}
	}
}
