package com.bentudou.tudoulive.api;

import com.bentudou.tudoulive.model.BtnToken;
import com.bentudou.tudoulive.model.ImgUrl;
import com.bentudou.tudoulive.model.Live;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lzz on 2016/9/19.
 */
public interface BenTuDouService {
    /**
     * 用户登录
     * @param userName
     * @param password
     */
    @GET("LiveUsers/loginAccount.htm")
    Call<BtnToken> userLogin(@Query("userName") String userName, @Query("password") String password);

    /**
     * 获取直播列表
     * @param LiveToken
     */
    @GET("LiveStraming/findUserLiveStramingListById.htm")
    Call<Live> findUserLiveStramingListById(@Query("LiveToken") String LiveToken);
    /**
     * 获取图片根地址
     */
    @GET("LiveStraming/getImgUrl.htm")
    Call<ImgUrl> getImgUrl();
}
