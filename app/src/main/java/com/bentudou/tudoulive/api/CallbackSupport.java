package com.bentudou.tudoulive.api;

import android.content.Context;
import android.view.View;

import com.bentudou.tudoulive.ui.view.ProgressHUD;
import com.bentudou.tudoulive.util.ToastUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lzz on 2016/9/19.
 */
public abstract class CallbackSupport<T> implements retrofit2.Callback<T> {
    /**
     * 用于标识来源
     */
    protected Integer tag;
    /**
     * 进度条对象
     */
    protected ProgressHUD progressHUD = null;
    /**
     * View控件
     */
    protected View view;
    /**
     *上下文
     */
    protected Context context;
    /**
     * <title>构造器</title>
     * <br>
     * @param context
     */
    public CallbackSupport(Context context){
        this.context = context;
    }
    /**
     * <title>构造器</title>
     * <br>如果progressHUD不为空的话就会关闭进度条
     * @param progressHUD
     * @param context
     */
    public CallbackSupport(ProgressHUD progressHUD, Context context){
        this.progressHUD = progressHUD;
        this.context = context;
    }
    /**
     * <title>构造器</title>
     * <br>如果progressHUD不为空的话就会关闭进度条
     * @param progressHUD 进度条
     * @param context 上下文
     * @param tag 来源标识
     */
    public CallbackSupport(ProgressHUD progressHUD, Context context, Integer tag){
        this.progressHUD = progressHUD;
        this.context = context;
        this.tag = tag;
    }

    /**
     *
     * @param progressHUD
     * @param context
     * @param view 重新加载按钮控件
     */
    public CallbackSupport(ProgressHUD progressHUD, Context context, View view){
        this.progressHUD = progressHUD;
        this.context = context;
        this.view = view;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (progressHUD != null)
            progressHUD.dismiss();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (progressHUD != null)
            progressHUD.dismiss();
        displayMessage();
    }
    //Toast提示错误信息
    private void displayMessage() {
        ToastUtils.showToastCenter(context,"服务器离家出走了,请稍后再试");
    }
}
