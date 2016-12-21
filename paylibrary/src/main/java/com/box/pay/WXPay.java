package com.box.pay;

import android.content.Context;
import android.content.Intent;

import com.box.pay.entity.WXPayParams;
import com.box.pay.impl.OnPayFinishListener;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by zer0 on 2016/12/16.
 */

public class WXPay {
    private static WXPay instance;

    private IWXAPI api;
    private OnPayFinishListener listener;

    public static WXPay getInstance(){
        if(instance==null){
            instance = new WXPay();
        }
        return instance;
    }

    public void init(Context context,String wxAppId){
        api = WXAPIFactory.createWXAPI(context,wxAppId);
    }

    public void handleIntent(Intent intent, IWXAPIEventHandler eventHandler){
        if(api!=null)api.handleIntent(intent, eventHandler);
    }

    public void onResp(BaseResp baseResp){
        if(listener==null)return;
        switch (baseResp.errCode){
            case 0:
                listener.onSuccess();
                break;
            case -2://取消
                listener.onCancel();
                break;
            default://错误
                listener.onFail(baseResp.errCode+"");
                break;
        }
    }

    public void pay(WXPayParams wxpayParams, OnPayFinishListener listener){
        if(api==null||wxpayParams==null||listener==null)return;
        this.listener = listener;
        PayReq req = new PayReq();
        req.appId			= wxpayParams.appid;
        req.partnerId		= wxpayParams.partnerid;
        req.prepayId		= wxpayParams.prepayid;
        req.nonceStr		= wxpayParams.noncestr;
        req.timeStamp		= wxpayParams.timestamp;
        req.packageValue	= wxpayParams.packagevalue;
        req.sign			= wxpayParams.sign;

        api.sendReq(req);
    }

}
