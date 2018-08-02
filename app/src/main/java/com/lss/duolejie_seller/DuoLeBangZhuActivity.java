package com.lss.duolejie_seller;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lss.duolejie_seller.base.BaseActivity;

/**
 * 入驻页
 * 
 * @author lss
 * 
 */
public class DuoLeBangZhuActivity extends BaseActivity {

	private WebView tencent_webviewx;
	private ImageView im_loginback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_duolebangzhu);
		initUI();

	}

	private void initUI() {
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		im_loginback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DuoLeBangZhuActivity.this.finish();
			}
		});
		tencent_webviewx = (WebView) findViewById(R.id.webview);
		String url = "http://39.107.14.118/appInterface/aboutUsContentInfo.jhtml?id=23";

		tencent_webviewx.loadUrl(url);
		WebSettings webSettings = tencent_webviewx.getSettings();
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setPluginState(PluginState.ON); // 设置webview支持插件
		tencent_webviewx.setWebChromeClient(new WebChromeClient());
		tencent_webviewx.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

}
