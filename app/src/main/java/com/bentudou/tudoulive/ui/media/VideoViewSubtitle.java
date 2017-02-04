package com.bentudou.tudoulive.ui.media;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bentudou.tudoulive.R;
import com.bentudou.tudoulive.util.DensityUtil;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by lzz on 2016/9/13.
 */
public class VideoViewSubtitle extends Activity implements  MediaPlayer.OnInfoListener, CompoundButton.OnCheckedChangeListener {
    private String path = "http://bktmgbxesdwugsd6u1aw.exp.bcelive.com/lss-gfdjf0ewdkgwdhbj/live.m3u8";
    private String cameraCode;
    private String cameraInfo;
    private Uri uri;
    private VideoView mVideoView;
    private ProgressBar pb;
    private TextView tv_camera_code,tv_no_content,tv_content;
    private FrameLayout fl_controller;
    private LinearLayout llt_tittle;
    private Button btn_tittle_back;
    private CheckBox checkboxQuanping;
    boolean isPortrait=true;
    private long mPosition = 0;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Vitamio.isInitialized(getApplicationContext());
        setContentView(R.layout.activity_vedio);
        initView();
    }

    private void initView() {
        path = getIntent().getStringExtra("url");
        cameraCode = getIntent().getStringExtra("camera_code");
        cameraInfo = getIntent().getStringExtra("camera_info");
        mVideoView = (VideoView) findViewById(R.id.buffer);
        fl_controller= (FrameLayout) findViewById(R.id.fl_controller);
        llt_tittle= (LinearLayout) findViewById(R.id.llt_tittle);
        btn_tittle_back= (Button) findViewById(R.id.btn_tittle_back);
        btn_tittle_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pb = (ProgressBar) findViewById(R.id.probar);
        checkboxQuanping = (CheckBox) findViewById(R.id.checkbox_quanping);
        checkboxQuanping.setOnCheckedChangeListener(this);
        tv_camera_code = (TextView) findViewById(R.id.tv_camera_code);
        tv_no_content = (TextView) findViewById(R.id.tv_no_content);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_camera_code.setText(cameraCode+"号摄像头");
        tv_content.setText(cameraInfo);
        if (path==""){
            return;
        }else {
            mVideoView.setVideoPath(path);
            mVideoView.requestFocus();
            mVideoView.setOnInfoListener(this);
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    mVideoView.setTimedTextShown(true);

                }
            });
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                pb.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                break;
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(!isPortrait){
                llt_tittle.setVisibility(View.VISIBLE);
                tv_no_content.setVisibility(View.VISIBLE);
                tv_content.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams fl_lp=new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        DensityUtil.dip2px(260,VideoViewSubtitle.this)
                );
                fl_controller.setLayoutParams(fl_lp);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
                isPortrait=true;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {
        mPosition = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mPosition > 0) {
            mVideoView.seekTo(mPosition);
            mPosition = 0;
        }
        super.onResume();
        mVideoView.start();
    }
    public  int getHeightPixel(Activity activity)
    {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.heightPixels;
    }
    public  int getWidthPixel(Activity activity)
    {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.widthPixels;
    }
    public  int getStatusBarHeight(Activity activity){
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            llt_tittle.setVisibility(View.GONE);
            tv_no_content.setVisibility(View.GONE);
            tv_content.setVisibility(View.GONE);
            LinearLayout.LayoutParams fl_lp = new LinearLayout.LayoutParams(
                    getHeightPixel(VideoViewSubtitle.this),
                    getWidthPixel(VideoViewSubtitle.this) - getStatusBarHeight(VideoViewSubtitle.this)
            );

            fl_controller.setLayoutParams(fl_lp);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
            isPortrait = false;

        } else {
            llt_tittle.setVisibility(View.VISIBLE);
            tv_no_content.setVisibility(View.VISIBLE);
            tv_content.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams fl_lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtil.dip2px(260,VideoViewSubtitle.this)
            );

            fl_controller.setLayoutParams(fl_lp);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
            isPortrait = true;
        }
    }
}
