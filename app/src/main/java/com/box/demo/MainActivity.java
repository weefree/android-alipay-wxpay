package com.box.demo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.box.demo.net.RespBody;
import com.box.pay.Alipay;
import com.box.pay.WXPay;
import com.box.pay.entity.AlipayParams;
import com.box.pay.entity.WXPayParams;
import com.box.pay.impl.OnPayFinishListener;
import com.box.demo.net.NetClient;
import com.example.paydemo.R;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnPayFinishListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WXPay.getInstance().init(this,Config.wxAppID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_btn:
                new AlertDialog.Builder(this).setItems(new String[]{"支付宝", "微信"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                //支付宝支付
                               doAlipay();
                                break;
                            case 1:
                                //微信支付
                               doWXPay();
                                break;
                        }
                    }
                }).create().show();
                break;
        }
    }

    private void doWXPay() {
        ProgressDialogFragment.newInstance().show(getFragmentManager());
        NetClient.getApi().getWxpayParams()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RespBody.WXPayParamsResp>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ProgressDialogFragment.hide(getFragmentManager());
                        Toast.makeText(MainActivity.this,"获取支付参数失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(RespBody.WXPayParamsResp wxPayParams) {
                        ProgressDialogFragment.hide(getFragmentManager());
                        if(wxPayParams==null||wxPayParams.data==null){
                            Toast.makeText(MainActivity.this,"参数错误",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!wxPayParams.data.isParamOk()){
                            Toast.makeText(MainActivity.this,"参数错误或不完整",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(wxPayParams.code == 0){
                            WXPay.getInstance().pay(wxPayParams.data, MainActivity.this);
                        }else{
                            Toast.makeText(MainActivity.this,wxPayParams.msg,Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void doAlipay() {
        ProgressDialogFragment.newInstance().show(getFragmentManager());
        NetClient.getApi().getAlipayParams()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AlipayParams>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,"获取支付参数失败",Toast.LENGTH_SHORT).show();
                        ProgressDialogFragment.hide(getFragmentManager());
                    }

                    @Override
                    public void onNext(AlipayParams alipayParams) {
                        if(alipayParams.code==0){
                            Alipay.getInstance().pay(MainActivity.this, alipayParams.data, MainActivity.this);
                        }else{
                            Toast.makeText(MainActivity.this,alipayParams.msg,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onSuccess() {
        Toast.makeText(MainActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
        ProgressDialogFragment.hide(getFragmentManager());
    }

    @Override
    public void onFail(String code) {
        Toast.makeText(MainActivity.this,"支付失败["+code+"]",Toast.LENGTH_SHORT).show();
        ProgressDialogFragment.hide(getFragmentManager());
    }

    @Override
    public void onCancel() {
        Toast.makeText(MainActivity.this,"用户取消支付",Toast.LENGTH_SHORT).show();
        ProgressDialogFragment.hide(getFragmentManager());
    }
}
