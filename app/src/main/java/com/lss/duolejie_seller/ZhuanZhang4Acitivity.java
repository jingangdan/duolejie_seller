package com.lss.duolejie_seller;

/**
 * 首页界面 lss 6.17
 */

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AnimateFirstDisplayListener;
import com.lss.duolejie_seller.utils.ImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ZhuanZhang4Acitivity extends BaseActivity {
	private TextView tv_wancheng,tv_skr,tv_yje;
	private ImageView im_tou;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();// 图片缓存

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhuanzhang3);
		initView();
	}

	private void initView() {
		im_tou = (ImageView) findViewById(R.id.im_tou);
		tv_wancheng = (TextView) findViewById(R.id.tv_wancheng);
		tv_skr = (TextView) findViewById(R.id.tv_skr);
		tv_yje = (TextView) findViewById(R.id.tv_yje);
		tv_skr.setText("收款人:"+getIntent().getStringExtra("shoukuanren"));
		tv_yje.setText("¥  "+getIntent().getStringExtra("jine"));
		try {
			ImageLoader.getInstance().displayImage(getIntent().getStringExtra("touxiang"), im_tou,ImageOptions.getOpstion(), animateFirstListener);
		} catch (Exception e) {
			
		}
		
		setListener(tv_wancheng);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_wancheng:
			ZhuanZhang4Acitivity.this.finish();
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	



}
