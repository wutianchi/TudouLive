package com.bentudou.tudoulive.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Grace on 2015/7/27.
 */
public class MyGridView extends GridView {

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                         MeasureSpec.AT_MOST);
           super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
