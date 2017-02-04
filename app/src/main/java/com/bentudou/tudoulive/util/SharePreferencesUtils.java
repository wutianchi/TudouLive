package com.bentudou.tudoulive.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lzz on 2016/9/19.
 */
public class SharePreferencesUtils {
    //保存token
    public static void saveBtdToken(Context context, String btd_token){
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("btd_token", btd_token);
        editor.commit();

    }
    //获取token
    public static String getBtdToken(Context context){

        String btd_token = null;
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        btd_token = sp.getString("btd_token", "");
        return btd_token;
    }
}
