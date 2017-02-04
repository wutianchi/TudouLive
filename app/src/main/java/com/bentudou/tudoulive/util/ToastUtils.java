package com.bentudou.tudoulive.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by lzz on 2016/7/27.
 */
public class ToastUtils {

    private static Toast toast;
    //toast正常显示
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context,content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
    //toast中部显示
    public static void showToastCenter(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context,content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
