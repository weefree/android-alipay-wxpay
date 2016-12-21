package com.box.demo.net;

import com.box.pay.entity.AlipayParams;
import com.box.pay.entity.WXPayParams;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zer0 on 2016/6/7.
 */
public interface ApiService {
    @POST("get_alipay_params")
    Observable<AlipayParams> getAlipayParams();

    @POST("get_wxpay_params")
    Observable<RespBody.WXPayParamsResp> getWxpayParams();




}
