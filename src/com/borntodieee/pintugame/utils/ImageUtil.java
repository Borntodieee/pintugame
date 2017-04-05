package com.borntodieee.pintugame.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.borntodieee.pintugame.R;
import com.borntodieee.pintugame.activity.PuzzleMain;
import com.borntodieee.pintugame.bean.ItemBean;

public class ImageUtil {
	
	/**
	 * 剪切图片（正常顺序）
	 * @param type 游戏各类
	 * @param picSelected 选择的图片
	 * @param context context
	 */
	public void createInitBitmaps(int type, Bitmap picSelected, Context context){
		Bitmap bitmap = null;
		List<Bitmap> bitmapItems = new ArrayList<Bitmap>();
		int itemWidth = picSelected.getWidth()/type;
		int itemHeight = picSelected.getHeight()/type;
		for(int j=0; j<type; j++){
			for(int i=0; i<type; i++){
				bitmap = Bitmap.createBitmap(picSelected, i*itemWidth, j*itemHeight, itemWidth, itemHeight);
				bitmapItems.add(bitmap);
				ItemBean itemBean = new ItemBean(j*type + i + 1, j*type + i + 1, bitmap);
				GameUtil.mItemBeans.add(itemBean);
			}
		}
		//保存最后一个图片在拼图完成时填充
		PuzzleMain.mLastBitmap = bitmapItems.get(type*type -1);
		//设置最后一个为空item
		bitmapItems.remove(type*type -1);
		GameUtil.mItemBeans.remove(type*type -1);
		Bitmap blankBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank);
		blankBitmap = Bitmap.createBitmap(blankBitmap, 0, 0, itemWidth, itemHeight);
		bitmapItems.add(blankBitmap);
		GameUtil.mItemBeans.add(new ItemBean(type*type, 0, blankBitmap));
		GameUtil.mBlankItemBean = GameUtil.mItemBeans.get(type*type -1);
	}
	
	/**
	 * 处理图片 放大、缩小到合适位置
	 * @param newWidth 缩放后的width
	 * @param newHeight 缩放后的height
	 * @param bitmap 要调整的bitmap
	 * @return
	 */
	public Bitmap resizeBitmap(float newWidth, float newHeight, Bitmap bitmap){
		Matrix matrix = new Matrix();
		matrix.postScale(newWidth/bitmap.getWidth(), newHeight/bitmap.getHeight());
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return newBitmap;
	}
}
