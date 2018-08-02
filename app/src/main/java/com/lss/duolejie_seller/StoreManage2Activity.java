package com.lss.duolejie_seller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;
import com.lss.duolejie_seller.utils.UIDialog;
import com.storemanager.MainConstant;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页-店铺管理
 * Created by jingang on 2018/7/28.
 */
public class StoreManage2Activity extends BaseActivity {
    @Bind(R.id.im_sm_back)
    ImageView imSmBack;
    @Bind(R.id.gv_sm)
    GridView gridView;
    @Bind(R.id.et_sm)
    EditText etSm;
    @Bind(R.id.et_sm_phone)
    EditText etSmPhone;
    @Bind(R.id.but_sm)
    Button butSm;

    private StoreManage2Activity TAG = StoreManage2Activity.this;

    private Context mContext;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private String imgPath;
    private boolean isClick = false;

    private static final int XIANGCE = 15;
    private static final int PAIZHAO = 14;

    public final int CODE_SELECT_IMAGE = 2;//相册RequestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manage);
        ButterKnife.bind(this);

        // initView();

        getGoodsBrandInfoData();

        mContext = this;
        initGridView();
    }

    private void initView() {
        imSmBack = (ImageView) findViewById(R.id.im_sm_back);
        gridView = (GridView) findViewById(R.id.gv_sm);
        etSm = (EditText) findViewById(R.id.et_sm);
        etSmPhone = (EditText) findViewById(R.id.et_sm_phone);
        butSm = (Button) findViewById(R.id.but_sm);

        butSm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etSm.getText().toString().trim())) {
                    Toast.makeText(TAG, "商家介绍不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etSmPhone.getText().toString().trim())) {
                    Toast.makeText(TAG, "联系方式不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isClick) {
                    updateBrand();
                }
            }
        });
    }


    /**
     * 获取商家数据
     */
    public void getGoodsBrandInfoData() {
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

    JsonHttpResponseHandler getGoodsBrandInfoData = new JsonHttpResponseHandler() {
        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.e("ssssss", "商家数据 = " + response);
            RequestDailog.closeDialog();
            String status = null;
            String desc = null;
            JSONObject dataxx = null;
            try {
                status = response.getString("status");
                desc = response.getString("desc");
                dataxx = response.getJSONObject("data");

            } catch (org.json.JSONException e) {
            }
            if (status.equals("0")) {
                try {
                    JSONObject data = dataxx.getJSONObject("goodsBrandInfoMap");

                    data.getString("phone");
                    data.getString("brand_introduction");
                    etSm.setText(data.getString("brand_introduction"));
                    etSmPhone.setText(data.getString("phone"));

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
            } else if (status.equals("111111")) {
                putSharedPreferenceValue("isLoged", "0");
                Toast.makeText(TAG, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
                Intent itziliao = new Intent();
                itziliao.setClass(TAG, B5_1_LoginActivity.class);
                startActivity(itziliao);
            } else {
                Tools.Notic(TAG, desc + "", null);
            }

        }

    };


    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过9张，才能点击
                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加9张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    //查看大图
    private void viewPluImg(int position) {
//        Intent intent = new Intent(mContext, PlusImageActivity.class);
//        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
//        intent.putExtra(MainConstant.POSITION, position);
//        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }


    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    /**
     * 打开相册或者照相机选择凭证图片，最多9张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(TAG,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            ActivityCompat.requestPermissions(TAG, PERMISSIONS_STORAGE, 1);
        }else{
            Intent intent_toXIANGCE = new Intent(Intent.ACTION_PICK, null);
            intent_toXIANGCE.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent_toXIANGCE, XIANGCE);
        }
    }



    @OnClick({R.id.im_sm_back, R.id.but_sm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_sm_back:
                this.finish();
                break;
            case R.id.but_sm:
                if (TextUtils.isEmpty(etSm.getText().toString().trim())) {
                    Toast.makeText(TAG, "商家介绍不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etSmPhone.getText().toString().trim())) {
                    Toast.makeText(TAG, "联系方式不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isClick) {
                    updateBrand();
                }
                break;
        }
    }

    public void updateBrand() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("line_brand_id", getSharedPreferenceValue("id"));
        map.put("brand_token", getSharedPreferenceValue("brand_token"));
        map.put("fieldName", "brand_introduction,phone,guide_img");
        map.put("fieldNameValue", etSm.getText().toString().trim() + "," + etSmPhone.getText().toString().trim() + "," + imgPath);
        map.put("updateFieldNameNum", "3");
        Long time = null;
        String jm = null;
        try {
            time = System.currentTimeMillis();
            System.out.println(time);
            String miwen = getSharedPreferenceValue("id") + "|$|" + getSharedPreferenceValue("brand_token") + "|$|" + "brand_introduction,phone,guide_img" + "|$|" + etSm.getText().toString().trim() + "," + etSmPhone.getText().toString().trim() + "," + imgPath + "|$|" + "2" + "|$|" + time.toString();
            jm = AESUtil.encrypt(miwen);

        } catch (Exception e) {

        }
        map.put("request_time", "" + time);
        map.put("sign", jm);

        String json = JsonUtils.toJson(map);
        HttpUtils.updateBrand(updateBrand, json);

        Log.e("ssssss","imgPath = "+imgPath);
    }

    JsonHttpResponseHandler updateBrand = new JsonHttpResponseHandler() {

        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.e("ssssss", "成功 = " + response);
            RequestDailog.closeDialog();
            String status = null;
            String desc = null;
            try {
                status = response.getString("status");
                desc = response.getString("desc");

            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            if (status.equals("0")) {
                // 成功
                isClick = false;
                Toast.makeText(TAG, "修改成功", Toast.LENGTH_LONG).show();
                TAG.finish();
            } else if (status.equals("111111")) {
                isClick = true;
                putSharedPreferenceValue("isLoged", "0");
                Toast.makeText(TAG, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
                Intent itziliao = new Intent();
                itziliao.setClass(TAG, DuoLeMainActivity.class);
                startActivity(itziliao);
            } else {
                //失败
                isClick = true;
                Tools.Notic(TAG, desc + "", null);
            }

        }

    };

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    JsonHttpResponseHandler uploadImgNew = new JsonHttpResponseHandler() {

        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.e("ssssss", "成功 = " + response.toString());
            RequestDailog.closeDialog();
            String status = null;
            String desc = null;
            String data = null;
            try {
                status = response.getString("status");
                desc = response.getString("desc");
                data = response.getString("data");
                if (TextUtils.isEmpty(imgPath)) {
                    imgPath = data;
                } else {
                    imgPath = imgPath + "@" + data;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            if (status.equals("0")) {
                //成功
                Toast.makeText(TAG, "图片上传成功！", Toast.LENGTH_LONG).show();
                mPicList.add(data); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            } else if (status.equals("111111")) {
                putSharedPreferenceValue("isLoged", "0");
                Toast.makeText(TAG, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
                Intent itziliao = new Intent();
                itziliao.setClass(TAG, DuoLeMainActivity.class);
                startActivity(itziliao);
            } else {
                //失败
                Tools.Notic(TAG, desc + "", null);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case XIANGCE:
                try {
                    startPhotoZoom(data.getData());
                } catch (Exception e) {
                    Toast.makeText(this, "您没有选择任何照片", Toast.LENGTH_LONG).show();
                }
                break;

            // 如果是调用相机拍照时
            case PAIZHAO:
                break;
        }
    }

    private String cutnameString;
    private String filename;
    private String timeString;

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
        putSharedPreferenceValue("guide_img", filename);

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

        Log.e("ssssss", "filenane = " + filename);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Bitmap photoBmp = null;
        if (uri != null) {
            try {
                photoBmp = getBitmapFormUri(TAG, uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable drawable = new BitmapDrawable(photoBmp);

            /**
             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
             */
            savaBitmap(photoBmp);
            //cim_mytouxiang.setImageBitmap(photoBmp);// 设置头像
        }
    }

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


    public class GridViewAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> mList;
        private LayoutInflater inflater;

        public GridViewAdapter(Context mContext, List<String> mList) {
            this.mContext = mContext;
            this.mList = mList;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            //return mList.size() + 1;//因为最后多了一个添加图片的ImageView
            int count = mList == null ? 1 : mList.size() + 1;
            if (count > MainConstant.MAX_SELECT_PIC_NUM) {
                return mList.size();
            } else {
                return count;
            }
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.pic_iv);
            if (position < mList.size()) {
                //代表+号之前的需要正常显示图片
                Log.e("ssssss", "img = " + mList.get(position));
                String picUrl = mList.get(position); //图片路径
                Glide.with(mContext).load(picUrl).into(iv);
            } else {
                iv.setImageResource(R.drawable.zj);//最后一个显示加号图片
            }
            return convertView;
        }
    }


}