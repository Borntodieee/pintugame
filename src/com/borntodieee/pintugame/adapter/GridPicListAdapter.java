package com.borntodieee.pintugame.adapter;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.borntodieee.pintugame.utils.ScreenUtil;

public class GridPicListAdapter extends ArrayAdapter<Bitmap>{

	public GridPicListAdapter(Context context, List<Bitmap> picList) {
		super(context, 0, picList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv_pic_item = null;
		int density = (int)ScreenUtil.getDeviceDensity(getContext());
		if(convertView == null){
			iv_pic_item = new ImageView(getContext());
			//设置布局图片
			iv_pic_item.setLayoutParams(new GridView.LayoutParams(80*density, 100*density));
			//设置显示比例类型
			iv_pic_item.setScaleType(ImageView.ScaleType.FIT_XY);
		}else{
			iv_pic_item = (ImageView)convertView;
		}
		iv_pic_item.setBackgroundColor(color.black);
		iv_pic_item.setImageBitmap(getItem(position));
		return iv_pic_item;
	}
	
	
	

}
