package com.bentudou.tudoulive.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bentudou.tudoulive.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lzz on 2016/9/19.
 */
public class ProgressHUD extends Dialog {
    public ProgressHUD(Context context) {
        super(context);
    }

    public ProgressHUD(Context context, int theme) {
        super(context, theme);
    }

    public void onWindowFocusChanged(boolean hasFocus){
//		ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
//        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
//        spinner.start();
    }

    /**
     * 更新消息内容
     * @param message 消息内容
     */
    public void setMessage(CharSequence message) {
        if(message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView)findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    /**
     * 显示进度提示框。
     * @param context
     * @param message 消息内容
     * @param cancelable 可取消
     * @param cancelListener 取消监听器
     * @return ProgressHUD
     */
    public static ProgressHUD show(final Context context, CharSequence message, boolean cancelable,
                                   OnCancelListener cancelListener) {
        final ProgressHUD dialog = new ProgressHUD(context, R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_hud);
        if(message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView)dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        dialog.setCanceledOnTouchOutside(false);//点其他位置，不消失
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity= Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.2f;
        dialog.getWindow().setAttributes(lp);
        //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {

            public void run() {

                dialog.dismiss(); // when the task active then close the dialog

                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report

            }

        }, 10000);

        return dialog;
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch(Exception e) {}//Fix bug of java.lang.IllegalArgumentException: View not attached to window manager
    }
}
