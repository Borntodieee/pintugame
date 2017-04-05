package com.borntodieee.pintugame.bean;

import android.graphics.Bitmap;


public class ItemBean {
	//ItemId拼图中每一小块的序号
	private int mItemId;
	//Bitmap的id
	private int mBitmapId;
	//Bitmap
	private Bitmap mBitmap;
	
	
	public ItemBean() {
		super();
	}
	public ItemBean(int mItemId, int mBitmapId, Bitmap mBitmap) {
		super();
		this.mItemId = mItemId;
		this.mBitmapId = mBitmapId;
		this.mBitmap = mBitmap;
	}
	
	public int getItemId() {
		return mItemId;
	}
	public void setItemId(int mItemId) {
		this.mItemId = mItemId;
	}
	public int getBitmapId() {
		return mBitmapId;
	}
	public void setBitmapId(int mBitmapId) {
		this.mBitmapId = mBitmapId;
	}
	public Bitmap getBitmap() {
		return mBitmap;
	}
	public void setBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}
	

}
