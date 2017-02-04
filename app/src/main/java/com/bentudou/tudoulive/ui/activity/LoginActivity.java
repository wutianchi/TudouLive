package com.bentudou.tudoulive.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.bentudou.tudoulive.R;
import com.bentudou.tudoulive.api.BenTuDouService;
import com.bentudou.tudoulive.api.CallbackSupport;
import com.bentudou.tudoulive.model.BtnToken;
import com.bentudou.tudoulive.retrofit.RTHttpClient;
import com.bentudou.tudoulive.ui.base.BaseTitleActivity;
import com.bentudou.tudoulive.ui.media.VideoViewSubtitle;
import com.bentudou.tudoulive.ui.view.ProgressHUD;
import com.bentudou.tudoulive.util.SharePreferencesUtils;
import com.bentudou.tudoulive.util.ToastUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lzz on 2016/9/19.
 */
public class LoginActivity extends BaseTitleActivity implements View.OnClickListener,TextWatcher,CompoundButton.OnCheckedChangeListener {
    private EditText et_login_name,etLoginPassword;
    private Button btnLogin;
    private CheckBox checkboxIspassword;
    private TextView tvForgetPassword;
    ProgressHUD progressHUD = null;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
        if (!SharePreferencesUtils.getBtdToken(this).equals("")){
            startActivity(new Intent(this,LiveListActivity.class));
            finish();
        }
    }

    @Override
    protected void initView() {
        super.setTitleText("登录");
        et_login_name = (EditText) findViewById(R.id.et_login_name);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        checkboxIspassword = (CheckBox) findViewById(R.id.checkbox_ispassword);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        et_login_name.setOnClickListener(this);
        etLoginPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        checkboxIspassword.setOnCheckedChangeListener(this);
        tvForgetPassword.setOnClickListener(this);
        et_login_name.addTextChangedListener(this);
        etLoginPassword.addTextChangedListener(this);
        btnLogin.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_login_name:
                break;
            case R.id.et_login_password:
                break;
            case R.id.btn_login:
                if (et_login_name.length()==0){
                    ToastUtils.showToastCenter(this, "请输入账号!");
                }else if (etLoginPassword.length()==0){
                    ToastUtils.showToastCenter(this,"请输入密码!");
                }else {
                    login();
                }
                break;
            case R.id.tv_forget_password:
                ToastUtils.showToastCenter(this, "请询问管理员!");
                break;
        }
    }

    //登录接口
    private void login() {
        progressHUD = ProgressHUD.show(this, "登录中", true, null);
        final BenTuDouService benTuDouService = RTHttpClient.create(BenTuDouService.class);
        Call<BtnToken> call=benTuDouService.userLogin(et_login_name.getText().toString(), etLoginPassword.getText().toString());
        call.enqueue(new CallbackSupport<BtnToken>(progressHUD,LoginActivity.this) {
            @Override
            public void onResponse(Call<BtnToken> call, Response<BtnToken> response) {
                super.onResponse(call,response);
                BtnToken btnToken=response.body();
                if (btnToken.getStatus().equals("1")){
                    SharePreferencesUtils.saveBtdToken(LoginActivity.this,btnToken.getData().getLiveToken());
                    startActivity(new Intent(LoginActivity.this, LiveListActivity.class));
                    finish();
                }else {
                    ToastUtils.showToastCenter(LoginActivity.this, btnToken.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<BtnToken> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            etLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        } else {
            etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
