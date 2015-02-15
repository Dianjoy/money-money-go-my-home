package com.mm.artifact;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * dialog工具 目前有如下对话框类型： R.layout.dialog_warning：一个文字提示+一个按钮
 * R.layout.dialog_invite：分享 R.layout.dialog_notice：一个文字提示+两个按钮
 * R.layout.loading_dialog：等待框，旋转小菊花
 * 
 * @author DianJoy-zl
 * 
 */
public class DialogUtil {
	private static AlertDialog dialog;

	public abstract interface BaseDialogListener {
	}

	/**
	 * 提示对话框，R.layout.dialog_style2
	 * 
	 * @author dianjoy-zl
	 * 
	 */
	public interface DialogStyle2Listener extends BaseDialogListener {
		/** 设置textview1显示的内容 */
		public void onSetTV1(TextView tv);

		/** 设置textview1显示的内容 */
		public void onSetTV2(TextView tv);

		/** 设置textview1显示的内容 */
		public void onSetTV3(TextView tv);

		/** 设置textview1显示的内容 */
		public void onSetTV4(TextView tv);

		/** textview3点击回调 */
		public void onClickTV3();

		/** textview4点击回调 */
		public void onClickTV4();
	}

	/**
	 * 提示对话框，R.layout.dialog_style1
	 * 
	 * @author dianjoy-zl
	 * 
	 */
	public interface DialogStyle1Listener extends BaseDialogListener {
		/** 设置第一个textview的显示内容 */
		public void onSetTV1(TextView tv);

		/** 设置第二个textview的显示内容 */
		public void onSetTV2(TextView tv);

		/** 设置第三个textview的显示内容 */
		public void onSetTV3(TextView tv);

		/** 点击按钮回调 */
		public void onClickTV3();
	}

	/**
	 * 提示对话框的接口，R.layout.dialog_notic样式的对话框
	 * 
	 * @author DianJoy-zl
	 * 
	 */
	public interface NoticDialogListener extends BaseDialogListener {

		/**
		 * 设置tv_notice_first的内容和状态
		 * 
		 * @param tv
		 */
		public void onSetFirstNotice(TextView tv);

		/**
		 * 设置tv_notice_second的内容和状态
		 * 
		 * @param tv
		 */
		public void onSetSecondNotic(TextView tv);

		/**
		 * 设置ok按钮显示的样式和文字
		 * 
		 * @param tv
		 */
		public void onSetButtonOkStyle(TextView tv);

		/**
		 * 设置cancel按钮显示的样式和文字
		 * 
		 * @param tv
		 */
		public void onSetButtonCancelStyle(TextView tv);

		/** 点击ok按钮的回调 */
		public void onClickOk();

		/** 点击cancel按钮的回调 */
		public void onClickCancel();
	}

	/**
	 * 分享对话框的回调接口，R.layout.dialog_invite样式的对话框
	 * 
	 * @author DianJoy-zl
	 * 
	 */
	public interface ShareDialogListener extends BaseDialogListener {
		/** 点击关闭按钮的回调 */
		public void onClickClose();

		/** 点击分享到微信朋友圈按钮的回调 */
		public void onClickWeiXinFriend();

		/** 点击分享到微信按钮的回调 */
		public void onClickWeiXin();

		/** 点击分享到短信按钮的回调 */
		public void onClickSms();

		/** 点击分享到新浪微博按钮的回调 */
		public void onClickSinaWeiBo();

		/** 点击分享到qq空间按钮的回调 */
		public void onClickQQzone();

		/** 点击分享到QQ按钮的回调 */
		public void onClickQQ();

	}

	/**
	 * 警告对话框的接口，R.layout.warning样式的对话框，有一个textview和一个button可以响应
	 * 
	 * @author DianJoy-zl
	 * 
	 */
	public interface WarningDialogListener extends BaseDialogListener {
		/**
		 * 设置textView的状态和显示的文字等
		 * 
		 * @param tv
		 */
		public void onSetTextView(TextView tv);

		/** 设置按钮textview的状态和显示的文字等 */
		public void onSetButtonState(TextView tv);

		/** 点击button的回调 */
		public void onClickButton();
	}

	/**
	 * 等待对话框的接口，R.layout.loading_dialog样式的对话框
	 * 
	 * @author DianJoy-zl
	 * 
	 */
	public interface LoadingDialogListener extends BaseDialogListener {
		public void onSetTextView(TextView tv);
	}

	/**
	 * 广告墙提示对话框的接口
	 * */
	public interface HBNoticeDialogListener extends BaseDialogListener {
		/** 点击button的回调 */
		public void onClickTV3();
	}

	/**
	 * 深度任务提示对话框的接口
	 * */
	public interface HBTaskDialogListener extends BaseDialogListener {
		public void click();

		public void close();

		public void setImageSrc(ImageView iv);

		public void setTitle(TextView tv);

		public void setMoney(TextView tv);

		public void setContent(TextView tv);
	}

	/**
	 * 创建并显示对话框
	 * 
	 * @param ctx
	 *            上下文
	 * @param index:  1---开启辅助功能后的对话框         2---有红包
	 *            对话框接口，不同类型的接口，将会展示不同样式的对话框
	 */
	public static void createAndShowDialog(final Context ctx,int index) {
		
		if(dialog != null && dialog.isShowing() == true){
			dismiss();
		}
		
		Builder builder = new AlertDialog.Builder(ctx);
		dialog = builder.create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(true);
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.alpha = 1.0f;
		lp.flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
		
		window.setAttributes(lp);
		View contentView = null;
		
		
		if(index == 2){// 红包提示
			Utils.addNotifyCounts(ctx);
			NotifyService.beginTime = System.currentTimeMillis();
			contentView = LayoutInflater.from(ctx).inflate(
					R.layout.hongbao_notify, null);
			
			TextView tv_open = (TextView) contentView.findViewById(R.id.tv_open);
			tv_open.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					long endTime = System.currentTimeMillis();
					long intral = endTime - NotifyService.beginTime;
					float thisTime = intral / 1000f;// 本次反应秒数
					// 计算平均速度
					int qiangCounts = Utils.getQiangCounts(ctx);// 当前抢红包的数
					float currentTime = Float.valueOf(Utils.getUseTime(ctx));// 当前平均时间
					float currentTotal = qiangCounts * currentTime;// 当前总时间
					float newIntral = (thisTime+currentTotal)/(qiangCounts+1)*1.0f;
					newIntral = (float)(Math.round(newIntral*10))/10;
					Utils.addQiangCounts(ctx);
					Utils.setUseTime(ctx, newIntral+"");
					Utils.startAPP(ctx, "com.tencent.mm");
					dismiss();
				}
			});
			
			TextView tv_ignor = (TextView) contentView.findViewById(R.id.tv_ignor);
			tv_ignor.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dismiss();
				}
			});
			
		}else if(index == 1) {// 开启辅助提示
			contentView = LayoutInflater.from(ctx).inflate(
					R.layout.assist_open_notify, null);
			TextView tv_ok = (TextView) contentView
					.findViewById(R.id.tv_ok);
			tv_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dismiss();
				}
			});
		}
		
		dialog.show();
		window.setContentView(contentView);
	}

	/**
	 * 使dialog消失
	 */
	public static void dismiss() {
		if (dialog != null) {
			if (dialog.isShowing() == true) {
				dialog.dismiss();
			}
		}
	}
}
