package com.bentudou.tudoulive.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.bentudou.tudoulive.R;
import com.bentudou.tudoulive.ui.base.BaseTitleActivity;
import com.bentudou.tudoulive.util.SharePreferencesUtils;

/**
 * Created by lzz on 2016/9/20.
 */
public class ExitActivity extends BaseTitleActivity implements View.OnClickListener {
    private Button btn_out_tudou;
    @Override
    protected void setContentView() {
        setContentView(R.layout.acitivyt_exit);
    }

    @Override
    protected void initView() {
        super.setTitleText("退出");
        btn_out_tudou = (Button) findViewById(R.id.btn_out_tudou);
        btn_out_tudou.setOnClickListener(this);
        title_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_out_tudou:
                SharePreferencesUtils.saveBtdToken(ExitActivity.this,"");
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.title_back:
                startActivity(new Intent(this,LiveListActivity.class));
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK==keyCode){
            startActivity(new Intent(this,LiveListActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
