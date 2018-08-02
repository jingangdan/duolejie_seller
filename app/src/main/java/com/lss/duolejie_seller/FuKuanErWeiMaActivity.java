package com.lss.duolejie_seller;

/**
 * 首页界面 lss 6.17
 */

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.AnimateFirstDisplayListener;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.ImageOptions;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;
import com.lss.duolejie_seller.view.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class FuKuanErWeiMaActivity extends BaseActivity {
    private CircularImage cim_touxiang;
    private TextView tv_shuoming, tv_shenqing, tv_xiazai;
    private ImageView im_erweima, im_loginback;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();// 图片缓存

    private static final int SAVE_SUCCESS = 0;// 保存图片成功
    private static final int SAVE_FAILURE = 1;// 保存图片失败
    private static final int SAVE_BEGIN = 2;// 开始保存图片

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_erweima);
        initView();
    }

    private void initView() {
        im_loginback = (ImageView) findViewById(R.id.im_loginback);
        tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);
        im_erweima = (ImageView) findViewById(R.id.im_erweima);
        tv_xiazai = (TextView) findViewById(R.id.tv_xiazai);
        tv_shenqing = (TextView) findViewById(R.id.tv_shenqing);
        try {
            ImageLoader.getInstance().displayImage(
                    getSharedPreferenceValue("qrcode"), im_erweima,
                    ImageOptions.getOpstion(), animateFirstListener);

        } catch (Exception e) {
            // TODO: handle exception
        }

        setListener(im_loginback, tv_shuoming, tv_xiazai, tv_shenqing);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.im_loginback:
                FuKuanErWeiMaActivity.this.finish();
                break;
            case R.id.tv_shuoming:
                Intent intent_to_login = new Intent(FuKuanErWeiMaActivity.this,
                        ShouKuanShuoMingActivity.class);
                startActivity(intent_to_login);
                break;
            case R.id.tv_xiazai:
                if (Build.VERSION.SDK_INT > 22) {
                    if (ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                                1);
                    } else {
                        setXiaZai();
                    }
                } else {
                    setXiaZai();
                }

                break;
            case R.id.tv_shenqing:
                Map<String, String> map = new HashMap<String, String>();
                map.put("line_brand_id", getSharedPreferenceValue("id"));
                Long time = null;
                String jm = null;
                try {

                    time = System.currentTimeMillis();
                    System.out.println(time);
                    String miwen = getSharedPreferenceValue("id") + "|$|" + time.toString();
                    jm = AESUtil.encrypt(miwen);

                } catch (Exception e) {

                }

                map.put("request_time", "" + time);
                map.put("sign", jm);
                String json = JsonUtils.toJson(map);
                HttpUtils.getLineBrandQRCode(getLineBrandQRCode, json);
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    //下载
    public void setXiaZai() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imgURl = getSharedPreferenceValue("qrcode");
                mHandler.obtainMessage(SAVE_BEGIN).sendToTarget();
                Bitmap bp = returnBitMap(imgURl);
                saveImageToPhotos(FuKuanErWeiMaActivity.this, bp);
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_BEGIN:
                    // ToastUtil.showMsg("开始保存图片...");
//				mSaveBtn.setClickable(false);
                    break;
                case SAVE_SUCCESS:
                    // ToastUtil.showMsg("图片保存成功,请到相册查找");
                    Tools.Notic(FuKuanErWeiMaActivity.this, "图片保存成功,请到相册查找", null);
//				mSaveBtn.setClickable(true);
                    break;
                case SAVE_FAILURE:
                    // ToastUtil.showMsg("图片保存失败,请稍后再试...");
                    Toast.makeText(FuKuanErWeiMaActivity.this, "图片保存失败,请稍后再试...", Toast.LENGTH_LONG).show();
//				mSaveBtn.setClickable(true);
                    break;
            }
        }
    };

    /**
     * 保存二维码到本地相册
     */
    private void saveImageToPhotos(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mHandler.obtainMessage(SAVE_FAILURE).sendToTarget();
            return;
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
    }

    /**
     * 将URL转化成bitmap形式
     *
     * @param url
     * @return bitmap type
     */
    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    JsonHttpResponseHandler getLineBrandQRCode = new JsonHttpResponseHandler() {

        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            // TODO Auto-generated method stub
            super.onSuccess(statusCode, headers, response);
            RequestDailog.closeDialog();
            String status = null;
            String desc = null;
            try {
                status = response.getString("status");
                desc = response.getString("desc");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status.equals("0"))// 成功
            {
                Tools.Notic(FuKuanErWeiMaActivity.this, "二维码申请成功,请耐心等待", null);

            } else if (status.equals("111111")) {
                putSharedPreferenceValue("isLoged", "0");
                Toast.makeText(FuKuanErWeiMaActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
                Intent itziliao = new Intent();
                itziliao.setClass(FuKuanErWeiMaActivity.this, DuoLeMainActivity.class);
                startActivity(itziliao);
            } else// 失败
            {
                Tools.Notic(FuKuanErWeiMaActivity.this, desc + "", null);
            }

        }

    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
                setXiaZai();
            } else {
                //权限被用户拒绝，做相应事情
                Toast.makeText(this, "请手动开启读写权限", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
