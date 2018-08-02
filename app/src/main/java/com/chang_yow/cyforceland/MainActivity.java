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

	public void forceLand()
	{
		if (!LandUtils.checkWriteSettingsPermission(MainActivity.this))
			startActivityForResult(LandUtils.preparePermissionIntent(MainActivity.this,
					  LandUtils.REQ_WRITE_SETTINGS), LandUtils.REQ_WRITE_SETTINGS);
		else
		{
			if (!LandUtils.checkDrawOverlayPermisson(MainActivity.this))
				startActivityForResult(LandUtils.preparePermissionIntent(MainActivity.this,
						  LandUtils.REQ_DRAW_OVERLAY), LandUtils.REQ_DRAW_OVERLAY);
			else
				LandUtils.forceOrientation(MainActivity.this, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == LandUtils.REQ_WRITE_SETTINGS)
			if (!LandUtils.checkDrawOverlayPermisson(MainActivity.this))
				startActivityForResult(LandUtils.preparePermissionIntent(MainActivity.this,
						  LandUtils.REQ_DRAW_OVERLAY), LandUtils.REQ_DRAW_OVERLAY);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		forceLand();
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
