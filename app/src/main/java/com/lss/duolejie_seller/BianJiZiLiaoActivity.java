package com.lss.duolejie_seller;

/**
 * 首页界面 lss 6.17
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.AnimateFirstDisplayListener;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.ImageOptions;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;
import com.lss.duolejie_seller.utils.UIDialog;
import com.lss.duolejie_seller.view.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class BianJiZiLiaoActivity extends BaseActivity {
    private CircularImage cim_mytouxiang;
    private ImageView im_loginback;
    private EditText et_shm;
    private TextView tv_baocun;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();// 图片缓存
    /* 头像上传 */
    private static final int PAIZHAO = 14;
    private static final int XIANGCE = 15;
    private String timeString;
    private String cutnameString;
    private String filename;
    private String fullPath = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_bianji);
        initView();
    }

    private void initView() {
        cim_mytouxiang = (CircularImage) findViewById(R.id.cim_mytouxiang);
        im_loginback = (ImageView) findViewById(R.id.im_loginback);
        et_shm = (EditText) findViewById(R.id.et_shm);
        tv_baocun = (TextView) findViewById(R.id.tv_baocun);
        et_shm.setText(getSharedPreferenceValue("brand_name"));
        try {
            ImageLoader.getInstance().displayImage(
                    getSharedPreferenceValue("brand_header"), cim_mytouxiang,
                    ImageOptions.getOpstion(), animateFirstListener);
            fullPath = getSharedPreferenceValue("brand_header");
        } catch (Exception e) {

        }
        setListener(cim_mytouxiang, im_loginback, tv_baocun);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.cim_mytouxiang:
                UIDialog.ForThreeBtn(this, new String[]{"相册", "拍照", "取消"}, this);
                break;
            case R.id.dialog_modif_1:// 相册
                UIDialog.closeDialog();
                /**
                 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
                 * 可以发现里面很多东西,Intent是个很强大的东西，大家一定仔细阅读下
                 */
                Intent intent_toXIANGCE = new Intent(Intent.ACTION_PICK, null);
                /**
                 * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.Images
                 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
                 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
                 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
                 */
                intent_toXIANGCE.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent_toXIANGCE, XIANGCE);
                break;
            case R.id.dialog_modif_2:// 拍照
                UIDialog.closeDialog();
                /**
                 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
                 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
                 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
                 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
                 */
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "'IMG'_yyyyMMddHHmmss");
                timeString = dateFormat.format(date);
                createSDCardDir();
                Intent intent_PAIZHAO = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent_PAIZHAO.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                        .fromFile(new File(Environment
                                .getExternalStorageDirectory() + "/DCIM/Camera",
                                timeString + ".jpg")));
                startActivityForResult(intent_PAIZHAO, PAIZHAO);
                break;
            case R.id.dialog_modif_3:// 取消
                UIDialog.closeDialog();
                break;
            case R.id.im_loginback:
                BianJiZiLiaoActivity.this.finish();
                break;
            case R.id.tv_baocun:
                String spname = et_shm.getText().toString().trim();
                if (TextUtils.isEmpty(spname)) {
                    Toast.makeText(BianJiZiLiaoActivity.this, "商户名称不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("line_brand_id", getSharedPreferenceValue("id"));
                    map.put("brand_token", getSharedPreferenceValue("brand_token"));
                    map.put("fieldName", "brand_name,brand_header");
                    map.put("fieldNameValue", spname + "," + fullPath);
                    map.put("updateFieldNameNum", "2");
                    Long time = null;
                    String jm = null;
                    try {

                        time = System.currentTimeMillis();
                        System.out.println(time);
                        String miwen = getSharedPreferenceValue("id") + "|$|" + getSharedPreferenceValue("brand_token") + "|$|" + "brand_name,brand_header" + "|$|" + spname + "," + fullPath + "|$|" + "2" + "|$|" + time.toString();
                        jm = AESUtil.encrypt(miwen);

                    } catch (Exception e) {

                    }

                    map.put("request_time", "" + time);
                    map.put("sign", jm);

                    String json = JsonUtils.toJson(map);
                    HttpUtils.updateBrand(updateBrand, json);
                }
                break;

            default:
                break;
        }
        super.onClick(v);
    }

    JsonHttpResponseHandler updateBrand = new JsonHttpResponseHandler() {

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

            } catch (org.json.JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (status.equals("0"))// 成功
            {
                Toast.makeText(BianJiZiLiaoActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                BianJiZiLiaoActivity.this.finish();
            } else if (status.equals("111111")) {
                putSharedPreferenceValue("isLoged", "0");
                Toast.makeText(BianJiZiLiaoActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
                Intent itziliao = new Intent();
                itziliao.setClass(BianJiZiLiaoActivity.this, DuoLeMainActivity.class);
                startActivity(itziliao);
            } else// 失败
            {
                Tools.Notic(BianJiZiLiaoActivity.this, desc + "", null);
            }

        }

    };

    // *****************************网络请求返回操作
    // end******************************************
    // *****************************onActivityResult操作
    // begin******************************************
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case XIANGCE:
                Log.e("ssssss", "data = " + data.getData());
                try {
                    startPhotoZoom(data.getData());
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(this, "您没有选择任何照片", Toast.LENGTH_LONG).show();
                }
                break;
            // 如果是调用相机拍照时
            case PAIZHAO:
                // File temp = new File(Environment.getExternalStorageDirectory()
                // + "/xiaoma.jpg");
                // 给图片设置名字和路径

                if (resultCode == RESULT_OK) {

                    File temp = new File(Environment.getExternalStorageDirectory()
                            .getPath() + "/DCIM/Camera/" + timeString + ".jpg");
                    startPhotoZoom(Uri.fromFile(temp));
                } else {

                }
                break;

        }
    }

    // *****************************onActivityResult操作
    // end******************************************
    // *****************************图像处理操作
    // begin******************************************

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Bitmap photoBmp = null;
        if (uri != null) {
            try {
                photoBmp = getBitmapFormUri(BianJiZiLiaoActivity.this, uri);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Drawable drawable = new BitmapDrawable(photoBmp);

            /**
             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
             */
            savaBitmap(photoBmp);
            cim_mytouxiang.setImageBitmap(photoBmp);// 设置头像
        }
    }

    public void createSDCardDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory();
            // 得到一个路径，内容是sdcard的文件夹路径和名字
            String path = sdcardDir.getPath() + "/DCIM/Camera";
            File path1 = new File(path);
            if (!path1.exists()) {
                // 若不存在，创建目录，可以在应用启动的时候创建
                path1.mkdirs();
            }
        }
    }

    // 将剪切后的图片保存到本地图片上！
    public void savaBitmap(Bitmap bitmap) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMddHHmmss");
        cutnameString = dateFormat.format(date);
        filename = Environment.getExternalStorageDirectory().getPath() + "/"
                + cutnameString + ".jpg";
        Tools.Log("filename=" + filename);
        File f = new File(filename);
        putSharedPreferenceValue("headImg_filename", filename);

        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);// 把Bitmap对象解析成流

        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpUtils.uploadImgNew(uploadImgNew, f);

        Log.e("ssssss", "filename = " + filename);
    }

    JsonHttpResponseHandler uploadImgNew = new JsonHttpResponseHandler() {

        public void onSuccess(int statusCode, Header[] headers,
                              org.json.JSONObject response) {
            // TODO Auto-generated method stub
            super.onSuccess(statusCode, headers, response);
            Log.e("ssssss", "成功 = " + response.toString());
            RequestDailog.closeDialog();
            String status = null;
            String desc = null;
            org.json.JSONObject data = null;
            try {
                status = response.getString("status");
                desc = response.getString("desc");
                fullPath = response.getString("data");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (org.json.JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (status.equals("0"))// 成功
            {
                Toast.makeText(BianJiZiLiaoActivity.this, "图片上传成功！",
                        Toast.LENGTH_LONG).show();

            } else if (status.equals("111111")) {
                putSharedPreferenceValue("isLoged", "0");
                Toast.makeText(BianJiZiLiaoActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
                Intent itziliao = new Intent();
                itziliao.setClass(BianJiZiLiaoActivity.this, DuoLeMainActivity.class);
                startActivity(itziliao);
            } else// 失败
            {
                Tools.Notic(BianJiZiLiaoActivity.this, desc + "", null);
            }

        }

    };

    JsonHttpResponseHandler getDengLu = new JsonHttpResponseHandler() {

        public void onSuccess(int statusCode, Header[] headers,
                              org.json.JSONObject response) {
            // TODO Auto-generated method stub
            super.onSuccess(statusCode, headers, response);
            RequestDailog.closeDialog();
            String status = null;
            String desc = null;
            try {
                status = response.getString("status");
                desc = response.getString("desc");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (org.json.JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (status.equals("0"))// 成功
            {
                Toast.makeText(BianJiZiLiaoActivity.this, "修改成功！",
                        Toast.LENGTH_LONG).show();

            } else if (status.equals("111111")) {
                putSharedPreferenceValue("isLoged", "0");
                Toast.makeText(BianJiZiLiaoActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
                Intent itziliao = new Intent();
                itziliao.setClass(BianJiZiLiaoActivity.this, DuoLeMainActivity.class);
                startActivity(itziliao);
            } else// 失败
            {
                Tools.Notic(BianJiZiLiaoActivity.this, desc + "", null);
            }

        }

    };

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri)
            throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;// optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        // 图片分辨率以480x800为标准
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        // 比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;// 设置缩放比例
        bitmapOptions.inDither = true;// optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);// 再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            // 第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差 ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
