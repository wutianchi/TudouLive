package com.bentudou.tudoulive.ui.base;

import android.view.View;

/**
 * Created by lzz on 2016/9/19.
 * 封装标题更新细节，避免子类接触相关的底层界面组件元素。
 */
public abstract class BaseTitleActivity extends BaseActivity {
    @Override
    protected void initTitle() {
        initNextClick();
    }

    protected void initNextClick() {
        title_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });
        title_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });
    }


    /**
     * 设置标题。
     * @param text
     */
    public void setTitleText(String text) {
        if(title_title != null) {
            title_title.setText(text);
        }
    }
    /**
     * 设置标题颜色。
     * @param color
     */
    public void setTitleTextColor(int color) {
        if(title_title != null) {
            title_title.setTextColor(color);
        }
    }
    /**
     * 设置头背景色
     * @param color
     */
    public void setTitleBackground(int color){

        if (title_layout != null){
            title_layout.setBackgroundColor(color);
        }
    }

    /**
     * 设置返回按钮标题。
     * @param text
     */
    public void setBackText(String text) {
        if(title_back != null) {
            title_back.setText(text);
        }
    }

    /**
     * 设置下一步按钮标题。
     * @param text
     */
    public void setNextText(String text) {
        if(title_next != null) {
            title_next.setText(text);
        }
    }
    /**
     * 设置baocun按钮标题。
     * @param text
     */
    public void setNextSave(String text) {
        if(title_save != null) {
            title_save.setText(text);
        }
    }

    /**
     * 下一步按钮被点击, 建议子类重载。
     */
    protected void onNextClick() {}
}
