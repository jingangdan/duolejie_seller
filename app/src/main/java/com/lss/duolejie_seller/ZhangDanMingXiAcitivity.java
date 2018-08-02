package com.lss.duolejie_seller;

/**
 * 首页界面 lss 6.17
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.view.XListView;
import com.lss.duolejie_seller.view.XListView.IXListViewListener;

public class ZhangDanMingXiAcitivity extends BaseActivity /*implements IXListViewListener*/{
	private XListView av_tixianlist1,av_tixianlist2;
	private TextView tv_skmx,tv_txmx;
	private ImageView im_loginback;

//	private List<VideoList> tiXianJiLus = new ArrayList<VideoList>();
//	private LiShiShouCangAdapter adapter;
	int startRowIndex = 1;
	private Handler mHandler = new Handler();// 异步加载或刷新

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhangndanmingxi);
		initView();
	}

	private void initView() {
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		av_tixianlist1 = (XListView) findViewById(R.id.av_tixianlist1);
		av_tixianlist2 = (XListView) findViewById(R.id.av_tixianlist2);
		tv_skmx = (TextView) findViewById(R.id.tv_skmx);
		tv_txmx = (TextView) findViewById(R.id.tv_txmx);

		Toast.makeText(ZhangDanMingXiAcitivity.this, "暂时没有收款明细数据", Toast.LENGTH_LONG).show();
		setListener(im_loginback,tv_txmx,tv_skmx);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.im_loginback:
			ZhangDanMingXiAcitivity.this.finish();
			break;
		case R.id.tv_skmx:
			tv_skmx.setTextColor(Color.parseColor("#FFFFFF")); 
			tv_txmx.setTextColor(Color.parseColor("#383838"));
			tv_skmx.setBackgroundColor(Color.parseColor("#F54d42"));
			tv_txmx.setBackgroundColor(Color.parseColor("#FFFFFF")); 
			Toast.makeText(ZhangDanMingXiAcitivity.this, "暂时没有收款明细数据", Toast.LENGTH_LONG).show();
			break;
		case R.id.tv_txmx:
			tv_skmx.setTextColor(Color.parseColor("#383838")); 
			tv_txmx.setTextColor(Color.parseColor("#FFFFFF")); 
			tv_skmx.setBackgroundColor(Color.parseColor("#FFFFFF")); 
			tv_txmx.setBackgroundColor(Color.parseColor("#F54d42")); 
			Toast.makeText(ZhangDanMingXiAcitivity.this, "暂时没有提现明细数据", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	

	/*@Override
	protected void onResume() {
		super.onResume();
		tiXianJiLus.clear();
		startRowIndex = 1;
		setPullDownLayout();
		
		HuoQuNeiRong();
	}

	
	public void HuoQuNeiRong(){
		*//** 参数：member_username（用户名，即手机号，必填）member_password（密码，必填）*//*
		Map<String, String> map = new HashMap<String, String>();
		map.put("brand_tel", mobile);
		map.put("password", mima);
		map.put("jpush_id", "123456");
		map.put("version", "1.0");
		String json = JsonUtils.toJson(map);
		HttpUtils.getFavoriteProductx(res_getFavoriteProduct,json);
	}

	
	*//**初始化下拉刷新,上拉加载组件*//*
	private void setPullDownLayout() {	
		xlistView.setDividerHeight(0);
		xlistView.setPullLoadEnable(true);
		xlistView.setXListViewListener(this);
	}

	@Override
	public void onRefresh() {
		*//** 下拉刷新 重建 *//*
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				tiXianJiLus.clear();
				startRowIndex = 1;
				HuoQuNeiRong();
				xlistView.stopRefresh();
			}
		}, 1000);
	}
	
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		*//** 上拉加载分页 *//*
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startRowIndex = startRowIndex + 1;
				HuoQuNeiRong();
				xlistView.stopLoadMore();
			}
		}, 1000);
	}*/


}
