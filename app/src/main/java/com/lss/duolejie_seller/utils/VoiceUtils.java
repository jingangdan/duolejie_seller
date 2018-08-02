package com.lss.duolejie_seller.utils;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

import com.lss.duolejie_seller.R;

public class VoiceUtils {
    private static volatile VoiceUtils singleton = null;
    public    boolean IsPlaying;

      MediaPlayer mediaPlayer=null;
    private Context mContext;

    public VoiceUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * 单例
     * @param context
     * @return
     */
    public static VoiceUtils with(Context context){
        if (singleton == null) {
            synchronized (VoiceUtils.class) {
                if (singleton == null) {
                    singleton = new VoiceUtils(context);
                }
            }
        }
        return singleton;
    }

public void SetIsPlay( boolean IsPlaying){

    this.IsPlaying=IsPlaying;
}

    public boolean GetIsPlay() {
        return IsPlaying;
    }
    public void Play(String stramount,boolean strsuccess)
    {



        String str=null;
        //如果是TRUE  就播放“收款成功”这句话
        if (strsuccess){
     str  =    "$" + PlaySound.capitalValueOf(Double.valueOf(String.format("%.2f", Double.parseDouble(stramount))));
}else {
     str  = PlaySound.capitalValueOf(Double.valueOf(String.format("%.2f", Double.parseDouble(stramount))));

}
        System.out.println("金额的长度 "+str);
        String temp ="";



        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        PlaySoundList(1,str,str.length());

    }
    public  void PlaySoundList( final int soundindex, final String soundString, final int soundcount)
    {
        singleton.SetIsPlay(true);
        boolean createState=false;
        if(mediaPlayer==null) {
            mediaPlayer = null;
        }
        System.out.println("加载音频["+soundindex+"]");
        mediaPlayer = createSound(soundindex,soundString);
        createState=true;

        if(createState==true)
            System.out.println("加载音频成功["+soundindex+"]");
        else
            System.out.println("加载音频失败["+soundindex+"]");

        //播放完成触发此事件
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();//释放音频资源
                int newsoundindex =soundindex;
                System.out.println("释放资源[" +soundindex+"]");
                if(soundindex<soundcount) {
                    newsoundindex=newsoundindex+1;
                    PlaySoundList(newsoundindex, soundString,soundcount);
                }else {
                    singleton.SetIsPlay(false);
                }

            }
        });
        try {
            //在播放音频资源之前，必须调用Prepare方法完成些准备工作
            if(createState)
                mediaPlayer.prepare();
            else
                mediaPlayer.prepare();
            //开始播放音频
            mediaPlayer.start();

            System.out.println("播放音频["+soundindex+"]");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  MediaPlayer createSound(int soundIndex, String soundString){
        MediaPlayer mp=null;

        String soundChar = soundString.substring(soundIndex-1,soundIndex);

        switch (soundChar)
        {
            case "零":
                mp=MediaPlayer.create(mContext,R.raw.a0);
                break;
            case "壹":
                mp=MediaPlayer.create(mContext,R.raw.a1);
                break;
            case "贰":
                mp=MediaPlayer.create(mContext,R.raw.a2);
                break;
            case "叁":
                mp=MediaPlayer.create(mContext,R.raw.a3);
                break;
            case "肆":
                mp=MediaPlayer.create(mContext,R.raw.a4);
                break;
            case "伍":
                mp=MediaPlayer.create(mContext,R.raw.a5);
                break;
            case "陆":
                mp=MediaPlayer.create(mContext,R.raw.a6);
                break;
            case "柒":
                mp=MediaPlayer.create(mContext,R.raw.a7);
                break;
            case "捌":
                mp=MediaPlayer.create(mContext,R.raw.a8);
                break;
            case "玖":
                mp=MediaPlayer.create(mContext,R.raw.a9);
                break;
            case "拾":
                mp=MediaPlayer.create(mContext,R.raw.ashi);
                break;
            case "佰":
                mp=MediaPlayer.create(mContext,R.raw.abai);
                break;
            case "仟":
                mp=MediaPlayer.create(mContext,R.raw.aqian);
                break;
            case "角":
                mp=MediaPlayer.create(mContext,R.raw.ajiao);
                break;
            case "分":
                mp=MediaPlayer.create(mContext,R.raw.afen);
                break;
            case "元":
                mp=MediaPlayer.create(mContext,R.raw.ayuan);
                break;
            case "整":
                mp=MediaPlayer.create(mContext,R.raw.azheng);
                break;
            case "万":
                mp=MediaPlayer.create(mContext,R.raw.awan);
                break;
            case "$":
                mp=MediaPlayer.create(mContext,R.raw.a);
                break;

        }
        //下面这三句是控制语速，但是只适用于Android6.0 以上，以下的就会报错，所以这个功能下次更新时解决
//        PlaybackParams pbp = new PlaybackParams();
//        pbp.setSpeed(1.5F);
//        mp.setPlaybackParams(pbp);
        mp.stop();
        return mp;
    }

}