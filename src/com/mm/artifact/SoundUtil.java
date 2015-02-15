package com.mm.artifact;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class SoundUtil {
	SoundPool soundPool;
	/** 抽奖时候的音效 */
	public static final int SOUND_TYPE_LOTTERY = 0;
	/** 得积分时候的音效 */
	public static final int SOUND_TYPE_EARN_MONEY = 1;
	HashMap<Integer, Integer> soundPoolMap = null;
	static Context mContext;
	private static final String TAG = SoundUtil.class.getSimpleName();
//	private boolean isLoaded = false;
	private SoundUtil() {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(SOUND_TYPE_EARN_MONEY,soundPool.load(mContext, R.raw.diaoluo_xiao, 1));
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
//				isLoaded = true;
			}
		});
	};

	private static class SoundUtilInstance {
		private static final SoundUtil instance = new SoundUtil();
	}

	public static SoundUtil getInstance(Context context) {
		mContext = context;
		return SoundUtilInstance.instance;
	}

	public void playSound(int type) {
//		if(isLoaded == false){
//			Logger.v(TAG, "但是没有准备好");
//			return;
//		}
		AudioManager mgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;

		// 参数：1、Map中取值 2、当前音量 3、最大音量 4、优先级 5、重播次数 6、播放速度
		switch (type) {
		case SOUND_TYPE_EARN_MONEY:
			soundPool.play(soundPoolMap.get(SOUND_TYPE_EARN_MONEY), volume,volume, 1, 1, 1f);
			break;
		case SOUND_TYPE_LOTTERY:
			soundPool.play(soundPoolMap.get(SOUND_TYPE_LOTTERY), volume,volume, 1, 1, 1f);
			break;
		default:
			break;
		}
	}

	public void stopSound(int type) {
		soundPool.stop(type);
	}
	
	public void release(){
		if(soundPool != null){
			soundPool.release();
		}
	}

}
