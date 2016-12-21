package com.box.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.box.pay.impl.OnPayFinishListener;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zer0 on 2016/12/14.
 */

public class Alipay {
    public static Alipay instance;


    public static Alipay getInstance(){
        if(instance == null){
            instance = new Alipay();
        }
        return instance;
    }

    /**
     * 9000	订单支付成功
     * 8000	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 4000	订单支付失败
     * 5000	重复请求
     * 6001	用户中途取消
     * 6002	网络连接出错
     * 6004	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 其它	其它支付错误
     */
    public void pay(final Activity activity, final String orderInfo, final OnPayFinishListener listener){

        Observable.create(new Observable.OnSubscribe<AlipayResult>() {
            @Override
            public void call(Subscriber<? super AlipayResult> subscriber) {
                PayTask alipay = new PayTask(activity);
                AlipayResult payResult = new AlipayResult(alipay.pay(orderInfo, true));
                subscriber.onNext(payResult);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AlipayResult>() {
                    @Override
                    public void call(AlipayResult result) {
                        if(listener==null)return;
                        if(TextUtils.equals(result.resultStatus,"9000")){
                            listener.onSuccess();
                        }else if(TextUtils.equals(result.resultStatus,"6001")){
                            listener.onCancel();
                        }else{
                            listener.onFail(result.resultStatus);
                        }
                    }
                });
    }

}
