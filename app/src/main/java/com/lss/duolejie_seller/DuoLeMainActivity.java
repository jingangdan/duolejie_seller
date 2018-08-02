package com.lss.duolejie_seller;

/**
 * 首页界面 lss 6.17
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.AnimateFirstDisplayListener;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.ImageOptions;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.ToastView;
import com.lss.duolejie_seller.utils.Tools;
import com.lss.duolejie_seller.utils.VoiceUtils;
import com.lss.duolejie_seller.view.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DuoLeMainActivity extends BaseActivity {
    private CircularImage cim_touxiang;
    private RelativeLayout rl_wodefukuanma, rl_dianpuguanli;
    private LinearLayout ll_zhangdan, ll_tixian, ll_lsdf;
    private TextView tv_shoukuan, tv_yue;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();// 图片缓存
    private ImageView view_view, im_smfk;
    private PullToRefreshScrollView sc;
    public Timer timer = null;

    private SharedPreferences sharedPreferences;
    private String user;//判断用户是否为首次进入

    private DuoLeMainActivity TAG = DuoLeMainActivity.this;
    private int PERMISSION_LOCATION = 0x01;
    private int PERMISSION_CAMERA = 0x02;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_duolemain);
        initView();

        sharedPreferences = getSharedPreferences("config", 0);
        user = sharedPreferences.getString("first", "1");

        if (user.equals("1")) {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermission();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first", "0");
            editor.commit();
        }

    }

    public void requestPermission() {
        //定位 相机和摄影 读  写
        if (ActivityCompat.checkSelfPermission(TAG, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(TAG, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(TAG, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                ||  ActivityCompat.checkSelfPermission(TAG, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||  ActivityCompat.checkSelfPermission(TAG, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TAG,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    PERMISSION_LOCATION);
        } else {
            //正常走
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
            } else {
                //权限被用户拒绝，做相应事情
            }
        }
        if(requestCode == PERMISSION_CAMERA){
            //相机和摄影
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                setIntent();
            } else {
                //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                Toast.makeText(DuoLeMainActivity.this, "请手动打开相机权限", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private Handler mHandlerxx = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            if (msg.what == 0) {
//				Toast.makeText(DuoLeMainActivity.this, "11111111", Toast.LENGTH_SHORT).show();
                try {
                    if (isLoged()) {

                    } else {
                        System.gc();
                        timer.cancel();
                    }
                } catch (Exception e) {

                }
                if (getSharedPreferenceValue("kaiguan").equals("1")) {

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("line_brand_id", getSharedPreferenceValue("id"));
                    map.put("brand_token", getSharedPreferenceValue("brand_token"));
                    Long time = null;
                    String jm = null;
                    try {

                        time = System.currentTimeMillis();
                        System.out.println(time);
                        String miwen = getSharedPreferenceValue("id") + "|$|" + getSharedPreferenceValue("brand_token") + "|$|" + time.toString();
                        jm = AESUtil.encrypt(miwen);

                    } catch (Exception e) {

                    }

                    map.put("request_time", "" + time);
                    map.put("sign", jm);

                    String json = JsonUtils.toJson(map);
                    HttpUtils.getLanguageMoneyList(getLanguageMoneyList, json);
                } else {
                    timer.cancel();
                }
            }
        }
    };

    JsonHttpResponseHandler getLanguageMoneyList = new JsonHttpResponseHandler() {

        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            // TODO Auto-generated method stub
            super.onSuccess(statusCode, headers, response);
            RequestDailog.closeDialog();
            String status = null;
            JSONObject data = null;
            String desc = null;
            String brand_token = null;
            try {
                status = response.getString("status");
                desc = response.getString("desc");
                data = response.getJSONObject("data");

            } catch (JSONException e) {

            }
            if (status.equals("0"))// 成功
            {

                try {
                    JSONArray jionb = data.getJSONArray("lineBrandQrcodeList");
                    for (int i = 0; i < jionb.length(); i++) {
                        VoiceUtils.with(DuoLeMainActivity.this).Play(jionb.getJSONObject(i).getString("pay_money"), true);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else// 失败
            {

            }

        }

    };

    private void initView() {
        cim_touxiang = (CircularImage) findViewById(R.id.cim_touxiang);
        rl_wodefukuanma = (RelativeLayout) findViewById(R.id.rl_wodefukuanma);
        rl_dianpuguanli = (RelativeLayout) findViewById(R.id.rl_dianpuguanli);
        ll_zhangdan = (LinearLayout) findViewById(R.id.ll_zhangdan);
        ll_tixian = (LinearLayout) findViewById(R.id.ll_tixian);
        ll_lsdf = (LinearLayout) findViewById(R.id.ll_lsdf);
        tv_shoukuan = (TextView) findViewById(R.id.tv_shoukuan);
        tv_yue = (TextView) findViewById(R.id.tv_yue);
        view_view = (ImageView) findViewById(R.id.view_view);
        im_smfk = (ImageView) findViewById(R.id.im_smfk);

		
		
		
		/*
         * sc =
		 * (PullToRefreshScrollView)findViewById(R.id.a2_fragment_shangxueyuan_sc
		 * );
		 * 
		 * WindowManager wm = DuoLeMainActivity.this.getWindowManager(); int
		 * screenWidth = wm.getDefaultDisplay().getWidth();
		 * ll_lsdf.setMinimumHeight(screenWidth);
		 * 
		 * 
		 * ILoadingLayout proxy = sc.getLoadingLayoutProxy(true,false);
		 * proxy.setPullLabel("下拉刷新"); proxy.setReleaseLabel("放开以加载...");
		 * proxy.setRefreshingLabel("玩命加载中....");
		 * proxy.setLastUpdatedLabel("最后的更新时间:"
		 * +DateFormat.getDateFormat(this.getApplicationContext()).format(new
		 * Date()));
		 * proxy.setLoadingDrawable(getResources().getDrawable(R.drawable
		 * .default_ptr_rotate));
		 * 
		 * 
		 * sc.setMode(Mode.PULL_FROM_START); sc.setOnRefreshListener(new
		 * OnRefreshListener<ScrollView>() {
		 * 
		 * @Override public void onRefresh(PullToRefreshBase<ScrollView>
		 * refreshView) {
		 * 
		 * // TODO下拉事件 new Thread(){ public void run() { try {
		 * Thread.sleep(1000);
		 * 
		 * } catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * mHandler.post(new RefredshDataRunnable(false)); }; }.start(); } });
		 */

        setListener(cim_touxiang, rl_wodefukuanma, rl_dianpuguanli,
                ll_zhangdan, ll_tixian, view_view, im_smfk);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.cim_touxiang:
                Intent intent_to_login = new Intent(DuoLeMainActivity.this,
                        ShangJiaZhongXinActivity.class);
                startActivity(intent_to_login);
                break;
            case R.id.im_smfk:
                //判断权限
                if (Build.VERSION.SDK_INT > 22) {
                    if (ActivityCompat.checkSelfPermission(TAG, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(DuoLeMainActivity.this,
                                new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA);
                    } else {
                        setIntent();
                    }

                } else {
                    setIntent();
                }

                break;
            case R.id.rl_wodefukuanma:
                Intent ints = new Intent(DuoLeMainActivity.this,
                        FuKuanErWeiMaActivity.class);
                startActivity(ints);
                break;
            case R.id.rl_dianpuguanli:
//                Toast.makeText(DuoLeMainActivity.this, "当前城市满足500家商户开放此功能！",
//                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(DuoLeMainActivity.this, StoreManage2Activity.class));
                break;
            case R.id.ll_zhangdan:
                Intent instssx = new Intent(DuoLeMainActivity.this,
                        ZhuanZhang1Acitivity.class);
                startActivity(instssx);
                break;
            case R.id.view_view:
                Intent intsxx = new Intent(DuoLeMainActivity.this,
                        DuoLeYueDuActivity.class);
                startActivity(intsxx);
                break;
            case R.id.ll_tixian:
                Intent instss = new Intent(DuoLeMainActivity.this,
                        TiXianAcitivity.class);
                startActivity(instss);
                break;
            case R.id.iv_phone:
                // 传入服务， parse（）解析号码
            /*
             * Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
			 * + store_phone)); //
			 * 通知activtity处理传入的call服务766666666666666666666666666666766666666666666666
			 * startActivity(intent1);
			 */
                break;

            default:
                break;
        }
        super.onClick(v);
    }

    public void setIntent() {
        Intent intsxxx = new Intent(DuoLeMainActivity.this,
                SaoMiaoActivity.class);
        startActivity(intsxxx);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
//                    setIntent();
//                } else {
//                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
//                    Toast.makeText(DuoLeMainActivity.this, "请手动打开相机权限", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                break;
//        }
//
//    }


    JsonHttpResponseHandler getGoodsBrandInfoData = new JsonHttpResponseHandler() {

        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            // TODO Auto-generated method stub
            super.onSuccess(statusCode, headers, response);
            RequestDailog.closeDialog();
            String status = null;
            String desc = null;
            JSONObject dataxx = null;
            try {
                status = response.getString("status");
                desc = response.getString("desc");
                dataxx = response.getJSONObject("data");

            } catch (JSONException e) {
                // TODO Auto-generated catch block

            }
            if (status.equals("0"))// 成功
            {

                try {
                    JSONObject data = dataxx.getJSONObject("goodsBrandInfoMap");
                    putSharedPreferenceValue("brand_header",
                            data.getString("brand_header"));
                    putSharedPreferenceValue("create_time",
                            data.getString("create_time"));
                    putSharedPreferenceValue("brand_money",
                            data.getString("brand_money"));
                    putSharedPreferenceValue("password",
                            data.getString("password"));
                    putSharedPreferenceValue("brand_tel",
                            data.getString("brand_tel"));
                    putSharedPreferenceValue("is_open",
                            data.getString("is_open"));
                    putSharedPreferenceValue("brand_address",
                            data.getString("brand_address"));
                    putSharedPreferenceValue("brand_name",
                            data.getString("brand_name"));
                    putSharedPreferenceValue("id", data.getString("id"));
                    putSharedPreferenceValue("merchant_name",
                            data.getString("merchant_name"));
                    putSharedPreferenceValue("jpush_id",
                            data.getString("jpush_id"));
                    putSharedPreferenceValue("brand_introduction",
                            data.getString("brand_introduction"));
                    putSharedPreferenceValue("guide_img",
                            data.getString("guide_img"));
                    putSharedPreferenceValue("qrcode", data.getString("qrcode"));
                    putSharedPreferenceValue("header", data.getString("header"));
                    putSharedPreferenceValue("brand_invite",
                            data.getString("brand_invite"));
                    putSharedPreferenceValue("pay_money",
                            data.getString("pay_money"));

                    tv_shoukuan.setText(getSharedPreferenceValue("pay_money"));
                    tv_yue.setText(getSharedPreferenceValue("brand_money"));
                    try {
                        ImageLoader.getInstance().displayImage(
                                getSharedPreferenceValue("brand_header"),
                                cim_touxiang, ImageOptions.getOpstion(),
                                animateFirstListener);
                    } catch (Exception e) {

                    }
                    try {
                        ImageLoader.getInstance()
                                .displayImage(
                                        getSharedPreferenceValue("header"),
                                        view_view, ImageOptions.getOpstion(),
                                        animateFirstListener);
                    } catch (Exception e) {

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else if (status.equals("111111")) {
                try {
                    if (timer != null) {
                        System.gc();
                        timer.cancel();

                    } else {

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                putSharedPreferenceValue("isLoged", "0");
                Toast.makeText(DuoLeMainActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
                Intent itziliao = new Intent();
                itziliao.setClass(DuoLeMainActivity.this, B5_1_LoginActivity.class);
                startActivity(itziliao);
            } else// 失败
            {
                Tools.Notic(DuoLeMainActivity.this, desc + "", null);
                // Tools.Notic(B5_MyActivity.this, error+"", null);
            }

        }

    };

    // 退出操作
    private boolean isExit = false;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                ToastView toast = new ToastView(getApplicationContext(),
                        "再按一次退出程序");
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Handler mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        isExit = false;
                    }
                };
                mHandler.sendEmptyMessageDelayed(0, 3000);
                return true;
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
                return false;
            }
        }
        return true;
    }

    // 自定义Handler发送的Runnable实现类
    class RefredshDataRunnable implements Runnable {

        private boolean isLoadMore;

        public RefredshDataRunnable(boolean isLoadMore) {
            this.isLoadMore = isLoadMore;
        }

        @Override
        public void run() {
            // TODO 在UI线程中执行的方法：刷新数据
            // RequestDailog.showDialog(mContext, "请稍后");
            if (!isLoadMore) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("line_brand_id", getSharedPreferenceValue("id"));
                map.put("brand_token", getSharedPreferenceValue("brand_token"));
                Long time = null;
                String jm = null;
                try {

                    time = System.currentTimeMillis();
                    System.out.println(time);
                    String miwen = getSharedPreferenceValue("id") + "|$|" + getSharedPreferenceValue("brand_token") + "|$|" + time.toString();
                    jm = AESUtil.encrypt(miwen);

                } catch (Exception e) {

                }

                map.put("request_time", "" + time);
                map.put("sign", jm);
                String json = JsonUtils.toJson(map);
                HttpUtils.getGoodsBrandInfoData(getGoodsBrandInfoData, json);
            }

            // 通知下拉刷新控件，数据已加载完成
            sc.onRefreshComplete();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                default:
                    break;
            }
        }
    };

    private void startTimer() {

        int tmie = 20000;
        try {
            tmie = Integer.parseInt(getSharedPreferenceValue("time"));
        } catch (Exception e) {
            tmie = 20000;
        }
        timer = new Timer();
        timer.schedule(new java.util.TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = 0;
                mHandlerxx.sendMessage(message);
            }
        }, 2000, tmie);
    }


    public boolean isLoged() {
        String isLoged = null;
        if (getSharedPreferenceValue("isLoged") != null) {
            isLoged = getSharedPreferenceValue("isLoged");
            if (isLoged.equals("1")) {
                return true;
            } else {
                return false;
            }
        } else {
            putSharedPreferenceValue("isLoged", "0");
            return false;
        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (isLoged()) {

            try {
                if (getSharedPreferenceValue("kaiguan").equals("1")) {
                    if (timer != null) {
                        System.gc();
                        timer.cancel();
                        startTimer();

                    } else {
                        startTimer();
                    }
                } else {
                    if (timer != null) {
                        System.gc();
                        timer.cancel();

                    } else {
                        System.gc();
                    }
                }

            } catch (Exception e) {
                putSharedPreferenceValue("kaiguan", "1");
                if (timer != null) {
                    timer.cancel();

                } else {
                }
                startTimer();
            }


            Map<String, String> map = new HashMap<String, String>();
            map.put("line_brand_id", getSharedPreferenceValue("id"));
            map.put("brand_token", getSharedPreferenceValue("brand_token"));

            Long time = null;
            String jm = null;
            try {

                time = System.currentTimeMillis();
                System.out.println(time);
                String miwen = getSharedPreferenceValue("id") + "|$|" + getSharedPreferenceValue("brand_token") + "|$|" + time.toString();
                jm = AESUtil.encrypt(miwen);

            } catch (Exception e) {

            }

            map.put("request_time", "" + time);
            map.put("sign", jm);
            String json = JsonUtils.toJson(map);
            HttpUtils.getGoodsBrandInfoData(getGoodsBrandInfoData, json);
        } else {
            try {
                System.gc();
                timer.cancel();

            } catch (Exception e) {
                // TODO: handle exception
            }
            Intent itdl = new Intent();
            itdl.setClass(DuoLeMainActivity.this, DuoLeLoginActivity.class);
            startActivity(itdl);
        }
    }
}
