package com.lss.duolejie_seller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.storemanager.MainConstant;
import com.storemanager.PictureSelectorConfig;
import com.storemanager.PlusImageActivity;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
public class StoremanageActivity extends BaseActivity {
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

    private StoremanageActivity TAG = StoremanageActivity.this;

    private Context mContext;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private String imgPath;
    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manage);
        ButterKnife.bind(this);
        getGoodsBrandInfoData();

        mContext = this;
        initGridView();
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
                    // TODO Auto-generated catch block
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

    /**
     * 打开相册或者照相机选择凭证图片，最多9张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick({R.id.im_sm_back, R.id.but_sm})
    public void onViewClicked(View view) {
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

    public void setPicList() {
        if (mPicList.size() > 0) {
            for (int i = 0; i < mPicList.size(); i++) {
                uploadImgNew(mPicList.get(i));
            }
        }
    }

    /**
     * 上传图片
     *
     * @param imgPath
     */
    public void uploadImgNew(String imgPath) {
        File f = new File(imgPath);
        putSharedPreferenceValue("guide_img", imgPath);

        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpUtils.uploadImgNew(uploadImgNew, f);
    }

    JsonHttpResponseHandler uploadImgNew = new JsonHttpResponseHandler() {

        public void onSuccess(int statusCode, Header[] headers,
                              org.json.JSONObject response) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调

                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    Log.e("ssssss", "结果回调 = " + mPicList.toString());
                    setPicList();
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
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
                Log.e("ssssss","img = "+mList.get(position));
                String picUrl = mList.get(position); //图片路径
                Glide.with(mContext).load(picUrl).into(iv);
            } else {
                iv.setImageResource(R.drawable.zj);//最后一个显示加号图片
            }
            return convertView;
        }
    }


}