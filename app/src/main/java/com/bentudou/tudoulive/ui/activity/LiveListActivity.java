package com.bentudou.tudoulive.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.bentudou.tudoulive.R;
import com.bentudou.tudoulive.adapter.TuDouLiveAdapter;
import com.bentudou.tudoulive.api.BenTuDouService;
import com.bentudou.tudoulive.api.CallbackSupport;
import com.bentudou.tudoulive.config.Constant;
import com.bentudou.tudoulive.model.BtnToken;
import com.bentudou.tudoulive.model.ImgUrl;
import com.bentudou.tudoulive.model.Live;
import com.bentudou.tudoulive.model.LiveData;
import com.bentudou.tudoulive.retrofit.RTHttpClient;
import com.bentudou.tudoulive.ui.base.BaseTitleActivity;
import com.bentudou.tudoulive.ui.media.VideoViewSubtitle;
import com.bentudou.tudoulive.ui.view.MyGridView;
import com.bentudou.tudoulive.ui.view.MyListView;
import com.bentudou.tudoulive.util.SharePreferencesUtils;
import com.bentudou.tudoulive.util.ToastUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lzz on 2016/9/19.
 */
public class LiveListActivity extends BaseTitleActivity implements View.OnClickListener {
    private SwipeRefreshLayout srllt_grow;
    private MyGridView mg_live;
    private List<LiveData> liveDataList;
    private TuDouLiveAdapter tuDouLiveAdapter;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_live_list);
    }

    @Override
    protected void initView() {
        super.setTitleText("实时直播");
        title_back.setVisibility(View.INVISIBLE);
        title_next.setText("设置");
        srllt_grow = (SwipeRefreshLayout) findViewById(R.id.srllt_grow);
        srllt_grow.setColorSchemeResources(R.color.color_select, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srllt_grow.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toRefresh();
            }
        });
        mg_live = (MyGridView) findViewById(R.id.mg_live);
        title_next.setOnClickListener(this);
        mg_live.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(LiveListActivity.this,VideoViewSubtitle.class)
                        .putExtra("url",liveDataList.get(i).getCameraLinkAddress()).putExtra("camera_code",liveDataList.get(i).getCameraCode()).putExtra("camera_info",liveDataList.get(i).getCameraCoverage()));
            }
        });
        initImg();
        initData();
    }

    private void initImg() {
        final BenTuDouService benTuDouService = RTHttpClient.create(BenTuDouService.class);
        Call<ImgUrl> call=benTuDouService.getImgUrl();
        call.enqueue(new CallbackSupport<ImgUrl>(this) {
            @Override
            public void onResponse(Call<ImgUrl> call, Response<ImgUrl> response) {
                super.onResponse(call,response);
                srllt_grow.setRefreshing(false);
                ImgUrl imgUrl=response.body();
                if (imgUrl.getStatus().equals("1")){
                    if (!imgUrl.getData().getImgUrl().isEmpty())
                    Constant.URL_BASE_IMG = imgUrl.getData().getImgUrl();
                }
            }

            @Override
            public void onFailure(Call<ImgUrl> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    private void toRefresh() {
        final BenTuDouService benTuDouService = RTHttpClient.create(BenTuDouService.class);
        Call<Live> call=benTuDouService.findUserLiveStramingListById(SharePreferencesUtils.getBtdToken(this));
        call.enqueue(new CallbackSupport<Live>(this) {
            @Override
            public void onResponse(Call<Live> call, Response<Live> response) {
                super.onResponse(call,response);
                srllt_grow.setRefreshing(false);
                Live live=response.body();
                if (live.getStatus().equals("1")){
                    liveDataList = live.getData();
                    tuDouLiveAdapter = new TuDouLiveAdapter(liveDataList,LiveListActivity.this);
                    mg_live.setAdapter(tuDouLiveAdapter);
                }else {
                    ToastUtils.showToastCenter(LiveListActivity.this, live.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<Live> call, Throwable t) {
                super.onFailure(call, t);
                srllt_grow.setRefreshing(false);
            }
        });
    }

    private void initData() {
        final BenTuDouService benTuDouService = RTHttpClient.create(BenTuDouService.class);
        Call<Live> call=benTuDouService.findUserLiveStramingListById(SharePreferencesUtils.getBtdToken(this));
        call.enqueue(new CallbackSupport<Live>(this) {
            @Override
            public void onResponse(Call<Live> call, Response<Live> response) {
                super.onResponse(call,response);
                Live live=response.body();
                if (live.getStatus().equals("1")){
                    liveDataList = live.getData();
                    tuDouLiveAdapter = new TuDouLiveAdapter(liveDataList,LiveListActivity.this);
                    mg_live.setAdapter(tuDouLiveAdapter);
                }else {
                    ToastUtils.showToastCenter(LiveListActivity.this, live.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<Live> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_next:
                startActivity(new Intent(LiveListActivity.this,ExitActivity.class));
                finish();
                break;
        }
    }

}
