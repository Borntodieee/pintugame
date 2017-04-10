package com.borntodieee.pintugame.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.borntodieee.pintugame.R;
import com.borntodieee.pintugame.adapter.GridPicListAdapter;
import com.borntodieee.pintugame.utils.ScreenUtil;

public class MainActivity extends Activity implements OnClickListener{
	
	// 返回码：系统图库
	private static final int RESULT_IMAGE = 100;
	// 返回码：相机
	private static final int RESULT_CAMERA = 200;
	//Image type
	private static final String IMAGE_TYPE = "image/*";
	// Temp照片路径
	public static String TEMP_IMAGE_PATH;
	// 游戏类型N*N
	private int mType = 2;
	// 显示Type
    private TextView mTvPuzzleMainTypeSelected;
    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopupWindow;
    private View mPopupView;
    private TextView mTvType2;
    private TextView mTvType3;
    private TextView mTvType4;
    
	private GridView mGridView;
	private List<Bitmap> mPicList;
	//初始化图片的id
	private int[] mResPicId;
 // 本地图册、相机选择
    private String[] mCustomItems = new String[]{"本地图册", "相机拍照"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TEMP_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/temp.png";
		mPicList = new ArrayList<Bitmap>();
		
		
		initView();
		mGridView.setAdapter(new GridPicListAdapter(MainActivity.this, mPicList));
		mGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position == mResPicId.length - 1){
					//选择本地图库、相机
					showDialogCustom();
				}else{
					//选择默认图片
					Intent intent = new Intent(MainActivity.this, PuzzleMain.class);
					intent.putExtra("picSelectedID", mResPicId[position]);
					intent.putExtra("mType", mType);
					startActivity(intent);
				}
			}
			
		});
		
		/**
         * 显示难度Type
         */
        mTvPuzzleMainTypeSelected.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 弹出popup window
                        popupShow(v);
                    }
                });
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == RESULT_IMAGE && data != null){
				//相册
				Cursor cursor = this.getContentResolver().query(data.getData(), null, null, null, null);
				cursor.moveToFirst();
				String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
				Intent intent = new Intent(MainActivity.this, PuzzleMain.class);
				intent.putExtra("mPicPath", imagePath);
				intent.putExtra("mType", mType);
				cursor.close();
				startActivity(intent);
			}else if(requestCode == RESULT_CAMERA){
				Log.i("onActivityResult", "222");
				//相机
				Intent intent = new Intent(MainActivity.this, PuzzleMain.class);
				intent.putExtra("mPicPath", TEMP_IMAGE_PATH);
				intent.putExtra("mType", mType);
				startActivity(intent);
			}
		}
	}



	/**
	 * 显示选择系统图库 相机对话框
	 */
	private void showDialogCustom(){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("选择：");
		builder.setItems(mCustomItems, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(0 == which){
					//选择本地图库
					Intent intent = new Intent(Intent.ACTION_PICK,null);
					intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
					startActivityForResult(intent,RESULT_IMAGE);
				}else if(1 == which){
					//选择系统相机
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					Uri photoUri = Uri.fromFile(new File(TEMP_IMAGE_PATH));
					intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
					startActivityForResult(intent,RESULT_CAMERA);
				}
				
			}
		});
		builder.create().show();
	}
	
	/**
	 * 初始化GridView
	 */
	private void initView(){
		mGridView = (GridView)findViewById(R.id.gv_xpuzzle_main_pic_list);
		//初始化Bitmap数据
		mResPicId = new int[]{R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,
				R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,
				R.drawable.pic8,R.drawable.pic9,R.drawable.pic10,R.drawable.pic11,
				R.drawable.pic12,R.drawable.pic13,R.drawable.pic14,R.drawable.pic15,R.drawable.pic16};
		Bitmap[] bitmaps = new Bitmap[mResPicId.length];
		
		/*
		 * 由于加载图片会造成会产生OOM(outofmemory)异常，
		 * 一般是，Bitmap加载图片最终都是通过java层的createBitmap来完成的，
		 * 需要消耗大量内存,我们可以利用BitmapFactory.Options限制图片的大小，降低图片质量，减少图片所占内存
		 * 例子：
		 * InputStream is = this.getResources().openRawResource(R.drawable.pic1); 
BitmapFactory.Options options=new BitmapFactory.Options(); 
options.inJustDecodeBounds = false; 
options.inSampleSize = 10;   //width，hight设为原来的十分一 
Bitmap btp =BitmapFactory.decodeStream(is,null,options); 
		 */

//		BitmapFactory.Options options=new BitmapFactory.Options(); 
//		options.inJustDecodeBounds = false; 
//		options.inSampleSize = 10;   //width，hight设为原来的十分一 
		for(int i=0; i<bitmaps.length; i++){
//书中原码
			bitmaps[i] = BitmapFactory.decodeResource(getResources(),mResPicId[i]);
//			InputStream is = this.getResources().openRawResource(mResPicId[i]); 
//			bitmaps[i] =BitmapFactory.decodeStream(is,null,options);
			mPicList.add(bitmaps[i]);
		}
		//显示type
		mTvPuzzleMainTypeSelected = (TextView)findViewById(R.id.tv_puzzle_main_type_selected);
		mLayoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// mType view
        mPopupView = mLayoutInflater.inflate(
                R.layout.xpuzzle_main_type_selected, null);
        mTvType2 = (TextView) mPopupView.findViewById(R.id.tv_main_type_2);
        mTvType3 = (TextView) mPopupView.findViewById(R.id.tv_main_type_3);
        mTvType4 = (TextView) mPopupView.findViewById(R.id.tv_main_type_4);
        // 监听事件
        mTvType2.setOnClickListener(this);
        mTvType3.setOnClickListener(this);
        mTvType4.setOnClickListener(this);
	}
	
	/**
	 * popup window item点击事件
	 */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Type
            case R.id.tv_main_type_2:
                mType = 2;
                mTvPuzzleMainTypeSelected.setText("2 X 2");
                break;
            case R.id.tv_main_type_3:
                mType = 3;
                mTvPuzzleMainTypeSelected.setText("3 X 3");
                break;
            case R.id.tv_main_type_4:
                mType = 4;
                mTvPuzzleMainTypeSelected.setText("4 X 4");
                break;
            default:
                break;
        }
        mPopupWindow.dismiss();
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

