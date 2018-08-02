package com.lss.duolejie_seller.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lss.duolejie_seller.R;
import com.lss.duolejie_seller.utils.AnimateFirstDisplayListener;
import com.lss.duolejie_seller.utils.ImageOptions;
import com.lss.duolejie_seller.view.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class TiXianAdapter extends BaseAdapter {
	
	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Activity c;
	String key;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();// 图片缓存
	public TiXianAdapter(Activity c, List<Map<String, Object>> data,String key) {
		this.c = c;
		this.data = data;
		this.key = key;
	}

	@Override
	public int getCount() {
//		Log.e("size", data.size()+"");
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(c);
            convertView = mInflater.inflate(R.layout.atixian_list_items, null);
            
            viewHolder.tv_rqz = (TextView) convertView.findViewById(R.id.tv_rqz);
            viewHolder.tv_dzz = (TextView) convertView.findViewById(R.id.tv_dzz);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_dzz.setText("已到账  "+data.get(position).get("surplusMoney"));
        String time = timedate(data.get(position).get("create_time").toString());
        viewHolder.tv_rqz.setText(time);
        
        
        return convertView;
	}

	private static class ViewHolder
    {
        TextView tv_dzz,tv_rqz;
    }
	
	public static String timedate(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		String times = sdr.format(new Date(lcc * 1L));
		return times;
 
	}
	
}
