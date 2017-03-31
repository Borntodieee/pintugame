package com.borntodieee.pintugame;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

import com.borntodieee.pintugame.utils.ScreenUtil;

public class MainActivity extends Activity {
	
	private PopupWindow mPopupWindow;
	private View mPopupView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/**
	 * 显示PopupWindow
	 * @param view popupwindow
	 */
	private void popupShow(View view){
		//获取屏幕density,转换成int类型
		int density = (int)ScreenUtil.getDeviceDensity(this);
		//显示PopupWindow
		mPopupWindow = new PopupWindow(mPopupView, 200*density, 50*density);
		//可聚焦，屏蔽掉除PopupWindow外的其它view的响应
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		//透明背景
		Drawable transparent = new ColorDrawable(Color.TRANSPARENT);
		mPopupWindow.setBackgroundDrawable(transparent);
		//获取位置
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0]-40*density, location[1]+30*density);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
