package com.mm.artifact;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weixintext.R;

public class Utils {

	public static String getShareText(Context context) {
		return "我用抢红包微信神器，自动抢了很多红包，获得称号:"
				+ getNickName(context)
				+ "，下载地址：http://www.hongbaosuoping.com/client_share/download/hbsq.html";
	}

	public static String getNickName(Context context) {
		String timeString = Utils.getUseTime(context);
		Float time = Float.valueOf(timeString);
		if (time == 0) {
			return "无称号";
		} else if (time <= 1) {
			return "风神";
		} else if (time <= 10) {
			return "闪电侠";
		} else if (time <= 60) {
			return "跑男";
		} else {
			return "蜗牛";
		}
	}

	// 震动
	public static void vibrate(Context context) {
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(500);
	}

	public static String getProcessName(Context context) {
		int user_id = android.os.Process.myUid();
		// Log.i("native", "java层进程的用户id:" + user_id);
		int pid = android.os.Process.myPid();
		// Log.i("native", "java层进程id:" + pid);
		return user_id + "";
		// ActivityManager mActivityManager = (ActivityManager) context
		// .getSystemService(Context.ACTIVITY_SERVICE);
		// for (ActivityManager.RunningAppProcessInfo appProcess :
		// mActivityManager
		// .getRunningAppProcesses()) {
		// if (appProcess.pid == pid) {
		// Log.i("native", "java层进程的用户id:"+appProcess.uid);
		// return appProcess.uid+"";
		// }
		// }
		// return null;
	}

	/*
	 * 启动一个app
	 */
	public static void startAPP(Context context, String appPackageName) {
		try {
			Intent intent = context.getPackageManager()
					.getLaunchIntentForPackage(appPackageName);
			context.startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(context, "没有安装", Toast.LENGTH_LONG).show();
		}
	}

	/** 设置打开辅助功能状态 */
	public static void setCloseAssist(Context context) {
		setPreferenceStr(context, Constants.IS_OPEN_ASSIST, "0");
	}

	/** 设置关闭辅助功能状态 */
	public static void setOpenAssistState(Context context) {
		setPreferenceStr(context, Constants.IS_OPEN_ASSIST, "1");
	}

	/** 是否开启辅助功能 */
	public static boolean isOpenAsist(Context context) {
		String is = getPreferenceStr(context, Constants.IS_OPEN_ASSIST, "0");

		if ("0".equals(is)) {
			return false;
		}

		return true;
	}

	public static int getNotifyCounts(Context context) {
		String notifyCounts = getPreferenceStr(context,
				Constants.NOTIFY_COUNTS, "0");
		return Integer.valueOf(notifyCounts);
	}

	public synchronized static void addNotifyCounts(Context context) {
		String notifyCounts = getPreferenceStr(context,
				Constants.NOTIFY_COUNTS, "0");
		int currentCount = Integer.valueOf(notifyCounts);
		currentCount++;
		setPreferenceStr(context, Constants.NOTIFY_COUNTS, currentCount + "");
	}

	public static int getQiangCounts(Context context) {
		String qiangCounts = getPreferenceStr(context, Constants.QIANG_COUNTS,
				"0");
		return Integer.valueOf(qiangCounts);
	}

	public synchronized static void addQiangCounts(Context context) {
		String qiangCounts = getPreferenceStr(context, Constants.QIANG_COUNTS,
				"0");
		int currentCount = Integer.valueOf(qiangCounts);
		currentCount++;
		setPreferenceStr(context, Constants.QIANG_COUNTS, "" + currentCount);
	}

	public static void setUseTime(Context context, String time) {
		setPreferenceStr(context, Constants.USE_TIME, time);
	}

	public static String getUseTime(Context context) {
		String useTime = getPreferenceStr(context, Constants.USE_TIME, "0");
		return useTime;
	}

	/** 是否是免打扰时段 */
	public static boolean isDontDisturbTime() {
//		Calendar date = Calendar.getInstance();// 获取当前时间
//		Calendar date1 = (Calendar) date.clone();// 复制
//		Calendar date2 = (Calendar) date.clone();// 复制
//		date1.set(Calendar.HOUR, 0);// 将一个时间设为当前8:00
//		date1.set(Calendar.MINUTE, 0);
//		date1.set(Calendar.SECOND, 0);
//		date2.set(Calendar.HOUR, 8);// 将第二个时间设为当前17:00
//		date2.set(Calendar.MINUTE, 0);
//		date2.set(Calendar.SECOND, 0);
//		if (date.after(date1) && date.before(date2)) {
//			return true;
//		}
//		return false;
		Date date = new Date();
		System.out.println(String.valueOf(date.getHours()));
		if (date.getHours() > 0 & date.getHours() < 8) {
			return true;
		}
		return false;
	}

	/** 震动开关 ,默认为开 */
	public static boolean getVibrateState(Context context) {
		String state = getPreferenceStr(context, Constants.IS_VIBRATE, "1");
		if ("1".equals(state)) {
			return true;
		}
		return false;
	}

	public static void setVibrateState(Context context, int state) {
		setPreferenceStr(context, Constants.IS_VIBRATE, state + "");
	}

	/** 获得抢红包开关的状态 */
	public static boolean getQHBState(Context context) {
		String state = getPreferenceStr(context, Constants.IS_QIANG_HB, "1");
		if ("1".equals(state)) {
			return true;
		}
		return false;
	}

	public static void setQHBState(Context context, int state) {
		setPreferenceStr(context, Constants.IS_QIANG_HB, state + "");
	}

	/** 获得是否免打扰 */
	public static boolean getDontDisturb(Context context) {
		String state = getPreferenceStr(context, Constants.IS_DONT_DISTURB, "1");
		if ("1".equals(state)) {
			return true;
		}
		return false;
	}

	public static void setDontDisturb(Context context, int state) {
		setPreferenceStr(context, Constants.IS_DONT_DISTURB, state + "");
	}

	public static OnTouchListener ImageTouchDark;
	public static OnTouchListener RelativeTouchDark;
	public static OnTouchListener ButtonTouchDark;
	public static OnTouchListener ImageButtonTouchDark;
	public static OnTouchListener TextViewTouchDark;

	public static OnTouchListener getTextViewListener(final Activity activity) {
		return TextViewTouchDark = new OnTouchListener() {

			public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50,
					0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
			public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_NOT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				}
				return false;
			}
		};
	}

	/**
	 * 点击ImageView添加的点击效果的listener
	 * 
	 * @param activity
	 * @return onTouchListener
	 */
	public static OnTouchListener getImageViewListener(final Activity activity) {
		return ImageTouchDark = new OnTouchListener() {

			public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50,
					0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
			public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView) activity.findViewById(v.getId());
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					iv.setColorFilter(new ColorMatrixColorFilter(
							BT_NOT_SELECTED));
					// iv.clearColorFilter();
				} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
					iv.setColorFilter(new ColorMatrixColorFilter(
							BT_NOT_SELECTED));
				}
				return false;
			}
		};
	}

	public static OnTouchListener getImageViewListener(final View view) {
		return ImageTouchDark = new OnTouchListener() {

			public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50,
					0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
			public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView) view.findViewById(v.getId());
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					iv.setColorFilter(new ColorMatrixColorFilter(
							BT_NOT_SELECTED));
					// iv.clearColorFilter();
				} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
					iv.setColorFilter(new ColorMatrixColorFilter(
							BT_NOT_SELECTED));
				}
				return false;
			}
		};
	}

	/**
	 * 判断当前网络连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getApplicationContext().getSystemService("connectivity"))
				.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isConnectedOrConnecting();
		}
		return false;
	}

	/** 屏幕的宽高 */
	public static int[] getResolution(Context context) {
		int[] arrayOfInt = new int[2];
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(localDisplayMetrics);
		arrayOfInt[0] = localDisplayMetrics.widthPixels;
		arrayOfInt[1] = localDisplayMetrics.heightPixels;
		return arrayOfInt;
	}

	/** 获取屏幕真实的宽高 */
	public static int[] getRealResolution(Context context) {
		int[] arrayOfInt = new int[2];
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		Class c;
		try {
			c = Class.forName("android.view.Display");
			Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, dm);
			arrayOfInt[0] = dm.widthPixels;
			arrayOfInt[1] = dm.heightPixels;
		} catch (Exception e) {

		}
		return arrayOfInt;
	}

	/**
	 * 获得屏幕的宽
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getFitWidth(Context context, int paramInt) {
		return paramInt * getResolution(context)[0] / 720;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
			return sdDir.toString();
		} else {
			return null;
		}
	}

	public static boolean isAvailable(String str) {
		return str != null && !str.trim().equals("");
	}

	public static int exchangeVerify(String phoneNum, String zfbName, int zfType) {
		// zfType=1 手机话费 zfType=2 QQ zfType=3 支付宝
		int status = 0; // 0字符串为空 1手机号不合法 2验证通过
		if (zfType == 1) {
			if (isAvailable(phoneNum)) {
				Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
				Matcher matcher = pattern.matcher(phoneNum);
				if (!matcher.matches()) {
					status = 1;
				} else {
					status = 2;
				}
			} else {
				status = 0;
			}
		} else if (zfType == 2) {
			if (isAvailable(phoneNum)) {
				status = 2;
			} else {
				status = 0;
			}
		} else if (zfType == 3) {
			if (isAvailable(phoneNum) && isAvailable(zfbName)) {
				status = 2;
			} else {
				status = 0;
			}
		}
		return status;
	}

	public static int setPasswordAvailable(String str) {
		if (str.length() < 6) {
			return 0;
		} else if (str.length() > 12) {
			return 1;
		} else {
			return 2;
		}
	}

	public static String getPreferenceStr(Context context, String name) {
		return getPreferenceStr(context, name, "0");
	}

	public static String getPreferenceStr(Context context, String name,
			String defValue) {
		SharedPreferences preferences = context.getSharedPreferences(
				"preferences", 0);
		return preferences.getString(name, defValue);
	}

	public static void setPreferenceStr(Context context, String name,
			String value) {
		SharedPreferences preferences = context.getSharedPreferences(
				"preferences", 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(name, value);
		editor.commit();
	}

	public static String MD5(String src) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			StringBuffer deviceIDString = new StringBuffer(src);
			src = convertToHex(md.digest(deviceIDString.toString().getBytes()));
		} catch (Exception e) {
			src = "00000000000000000000000000000000";
		}
		return src;
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static boolean deleteContent() {
		try {
			String path = Utils.getSDPath();
			File file = new File(path + "/android/logg.bat");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write("".getBytes());
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getScore(int score) {
		if (score > 10000 * 100) {
			return score / 100 + "";
		} else if (score > 1000 * 100) {
			DecimalFormat fnum = new DecimalFormat("##0.0");
			return fnum.format((score / 100.0f));
		} else {
			DecimalFormat fnum = new DecimalFormat("##0.00");
			return fnum.format((score / 100.0f));
		}

	}

	/** 网络是否为wifi */
	public static boolean isWiFi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	public static byte[] getDeParams(byte[] data, byte[] key) {
		byte[] arr = new byte[data.length];
		// arr = Arrays.copyOf(data, data.length);
		System.arraycopy(data, 0, arr, 0, data.length);
		int keylen = key.length;
		int len = arr.length;
		for (int i = 0; i < len; ++i) {
			arr[i] = (byte) (arr[i] - key[i % keylen]);
			arr[i] = (byte) (arr[i] - keylen);
			arr[i] = (byte) (arr[i] ^ 36);
		}
		return arr;
	}

	public static byte[] getEnParams(byte[] data, byte[] key) {
		// 以下防止反编译代码
		String line;
		BufferedReader input = null;
		try {
			Process p = Runtime.getRuntime().exec("getprop");
			input = new BufferedReader(
					new InputStreamReader(p.getInputStream()), 1024);
			line = input.readLine();
			input.close();
		} catch (IOException ex) {

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {

				}
			}
		}
		// 以上防止反编译
		byte[] arr = new byte[data.length];
		// arr = Arrays.copyOf(data, data.length);
		System.arraycopy(data, 0, arr, 0, data.length);
		int keylen = key.length;
		int len = arr.length;
		for (int i = 0; i < len; ++i) {
			arr[i] = (byte) (arr[i] ^ 36);
			arr[i] = (byte) (arr[i] + key[i % keylen]);
			arr[i] = (byte) (arr[i] + keylen);
		}
		return arr;
	}

	public static void savePhotoToSDCard(String path, String photoName,
			Bitmap photoBitmap) {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File photoFile = new File(path, photoName);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String readContentFromAssets(Context context) {
		try {
			InputStream is = context.getAssets().open("lock");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String len = "";
			StringBuilder sb = new StringBuilder();
			while ((len = reader.readLine()) != null) {
				sb.append(len);
			}
			reader.close();
			is.close();
			return sb.toString();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return "";
	}

	/** 获取默认邀请码 */
	public static String getInviteID(Context context) {
		String content = readContentFromAssets(context);
		if (content != null && !content.trim().equals("")) {
			String[] strs = content.split(",");
			return strs[0];
		}
		return "";
	}

	/** 获取渠道号 */
	public static String getChannelID(Context context) {
		String content = readContentFromAssets(context);
		if (content != null && !content.trim().equals("")) {
			String[] strs = content.split(",");
			if (strs.length == 2) {
				return strs[1];
			}
		}
		return "";
	}

	public static long parseTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String formatTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}

	/** 创建错误提示dialog */

	public static AlertDialog createErrorDialog(Context context,
			String description) {
		Builder builder = new AlertDialog.Builder(context);
		final AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);

		int dialog_width = Utils.dip2px(context, 270);
		int dialog_height = Utils.dip2px(context, 140);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = dialog_width;
		lp.height = dialog_height;
		lp.alpha = 1.0f;
		window.setAttributes(lp);
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.dialog_warning, null);
		TextView tv_warning = (TextView) contentView
				.findViewById(R.id.tv_warning);
		LinearLayout ll_ok = (LinearLayout) contentView
				.findViewById(R.id.ll_warning_ok);
		ll_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		tv_warning.setText(description);
		window.setContentView(contentView);
		return dialog;
	}

	public static void showToast(Activity activity, String content, int duration) {
		ToastUtil.showToast(activity, content, duration);
	}

	/** Toast提示 */
	public static void showToast(Activity activity, String content) {
		showToast(activity, content, 1500);
	}

	/** activity跳转 */
	public static void startNewActivity(Context context, Class<?> clazz) {
		startNewActivity(context, clazz, -1, null);
	}

	public static void startNewActivity(Context context, Class<?> clazz,
			int flags) {
		startNewActivity(context, clazz, flags, null);
	}

	public static void startNewActivity(Context context, Class<?> clazz,
			Bundle bundle) {
		startNewActivity(context, clazz, -1, bundle);
	}

	public static void startNewActivity(Context context, Class<?> clazz,
			int flags, Bundle bundle) {
		Intent intent = new Intent(context, clazz);
		if (flags != -1) {
			intent.addFlags(flags);
		}
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	/** TextView设置字体 */
	public void setTypefaceRegular(TextView tv, Context context) {
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(),
				"Roboto-Regular.ttf"));
	}

	public void setTypefaceThin(TextView tv, Context context) {
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(),
				"Roboto-Thin.ttf"));
	}

	public static void openAPP(Context context, String packagename) {
		PackageManager localPackageManager = context.getPackageManager();
		Intent localIntent = localPackageManager
				.getLaunchIntentForPackage(packagename);
		if (localIntent != null) {
			context.startActivity(localIntent);
		}
	}

	public static void uninstall(Context context, String packagename) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_DELETE);
		intent.setData(Uri.parse("package:" + packagename));
		context.startActivity(intent);
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 字符串的压缩
	 * 
	 * @param str
	 *            待压缩的字符串
	 * @return 返回压缩后的字符串
	 * @throws IOException
	 */
	public static byte[] compress(String str) throws IOException {
		// 创建一个新的 byte 数组输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 使用默认缓冲区大小创建新的输出流
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		// 将 b.length 个字节写入此输出流
		gzip.write(str.getBytes());
		gzip.close();
		// 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
		return out.toByteArray();
		// return out.toString("ISO-8859-1");
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	// 获取渠道号是否为kuaiya
	public static boolean isKuaiYa(Context context) {
		String channel = Utils.getChannelID(context);
		if ("kuaiya".equals(channel)) {
			return true;
		}
		return false;
	}

	// 复制apk
	public static void copyApk(Context context) {
		File file = null;
		try {
			InputStream is = context.getAssets().open("hongbao.jpg");
			String tempPath = Utils.getSDPath();
			if (tempPath != null && !tempPath.trim().equals("")) {
				File tempFile = new File(tempPath + "/download");
				if (!tempFile.exists()) {
					tempFile.mkdirs();
				}
				file = new File(tempPath + "/download", "test.apk");
				if (file.exists()) {
					return;
				}
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024 * 4];
				int length = 0;
				while ((length = is.read(buffer)) > 0) {
					fos.write(buffer, 0, length);
					fos.flush();
				}
				is.close();
				fos.close();
			}
		} catch (IOException e) {
			if (file != null && file.exists()) {
				file.delete();
			}
		}
	}

	// 打开apk安装页面
	public static boolean startOpen(Context context) {
		try {
			String tempPath = Utils.getSDPath();
			File file = new File(tempPath + "/download", "test.apk");
			if (file.exists()) {
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(file),
						"application/vnd.android.package-archive");
				context.startActivity(intent);
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}
}
