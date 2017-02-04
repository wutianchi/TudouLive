package com.bentudou.tudoulive.ui.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bentudou.tudoulive.R;

/**
 * Created by lzz on 2016/9/19.
 * Activity基础类,自定义标题栏，弹出对话框，友盟统计
 */
public abstract class BaseActivity extends Activity {
    protected Button title_back,title_next,title_save;
    protected TextView title_title ;
    protected RelativeLayout title_layout;
    protected Application application = null;
    /**Context 上下文对象 */
    protected Context context = null;
    /**布局inflater*/
    protected LayoutInflater layoutInflater = null;
    /**输入法管理对象,主要用于弹出和隐藏软键盘*/
    protected InputMethodManager tInputMethodManager = null;

    /**
     * 弹出对话框提示信息
     *  @param titleID title
     * @param contextID context
     */
    protected void showDialog(int titleID,int contextID) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(titleID));
        dialog.setMessage(getString(contextID));
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView();
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        context = this;
        layoutInflater = LayoutInflater.from(context);
        tInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        findTitleView();
        initTitle();
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_MENU ){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 启动activity
     * @param action
     * @param extras
     */
    protected void startActivity(String action, Bundle extras) {
        Intent intent = new Intent();
        intent.setAction(action);
        if(null != extras) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    public void startActivity(Context activity,Class target,Bundle extras){
        Intent intent = new Intent();
        intent.setClass(activity, target);
        if(null!=extras){
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    /** 初始化 标题  */
    private void findTitleView(){
        title_layout = (RelativeLayout) findViewById(R.id.title_layout);
        title_back=(Button)findViewById(R.id.title_back);
        title_title=(TextView)findViewById(R.id.title_title);
        title_next=(Button)findViewById(R.id.title_next);
        title_save=(Button)findViewById(R.id.title_save);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        context = null;
        tInputMethodManager = null;
        layoutInflater = null;
        super.onDestroy();
    }



    /** 设置界面布局， 第一个调用 */
    protected abstract void setContentView();

    /** 设置title 内容和处理事件, 第二个调用 */
    protected abstract void initTitle();

    /** 初始化view，最后调用 */
    protected abstract void initView();
}
