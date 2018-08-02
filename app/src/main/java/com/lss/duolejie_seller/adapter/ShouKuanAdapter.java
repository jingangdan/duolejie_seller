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

public class ShouKuanAdapter extends BaseAdapter {
	
	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Activity c;
	String key;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();// 图片缓存
	public ShouKuanAdapter(Activity c, List<Map<String, Object>> data,String key) {
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
            convertView = mInflater.inflate(R.layout.ashoukuan_list_items, null);
            
            viewHolder.cim_mytouxiang = (CircularImage) convertView.findViewById(R.id.cim_mytouxiang);
            viewHolder.tv_sjm = (TextView) convertView.findViewById(R.id.tv_sjm);
            viewHolder.tv_rq = (TextView) convertView.findViewById(R.id.tv_rq);
            viewHolder.tv_jia = (TextView) convertView.findViewById(R.id.tv_jia);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            ImageLoader.getInstance().displayImage((String) data.get(position).get("user_header"), viewHolder.cim_mytouxiang,ImageOptions.getOpstion(), animateFirstListener);
		} catch (Exception e) {
			
		}
        viewHolder.tv_sjm.setText(data.get(position).get("nick_name").toString());
        String time = timedate(data.get(position).get("create_time").toString());
        viewHolder.tv_rq.setText(time);
        viewHolder.tv_jia.setText(data.get(position).get("pay_money").toString());
        
        
        return convertView;
	}

	private static class ViewHolder
    {
		CircularImage cim_mytouxiang;
        TextView tv_sjm,tv_rq,tv_jia;
    }
	
	public static String timedate(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		String times = sdr.format(new Date(lcc * 1L));
		return times;
 
	}
	
}
