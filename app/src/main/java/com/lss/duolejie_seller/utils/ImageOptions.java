package com.lss.duolejie_seller.utils;

import com.lss.duolejie_seller.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/** 异步加载图片属性的设置 */
public class ImageOptions {
    public static DisplayImageOptions options;

	public static DisplayImageOptions getLogoOptions(boolean round) {
		DisplayImageOptions.Builder m_options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true);
		if (round) {// 如果是圆角
			m_options.displayer(new RoundedBitmapDisplayer(5));
		}
		return m_options.build();
	}

	/**
	 * 
	 * @param round
	 *            true是圆角
	 * @return
	 */
	public static DisplayImageOptions getGoodsOptions(boolean round) {
		DisplayImageOptions.Builder m_options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true);
		if (round) {// 如果是圆角
			m_options.displayer(new RoundedBitmapDisplayer(5));
		}
		return m_options.build();
	}
    
    public static DisplayImageOptions getOpstion(){
    	if(options == null){
        	options = new DisplayImageOptions.Builder()
    			.showImageOnLoading(R.drawable.ic_launcher)
    			.showImageForEmptyUri(R.drawable.ic_launcher)
    			.showImageOnFail(R.drawable.ic_launcher)
    			.cacheInMemory(true)
    			.cacheOnDisk(true)
    			.considerExifParams(true)
    			.displayer(new RoundedBitmapDisplayer(5))
    			.build();
    	}
    	return options;
    }
}
