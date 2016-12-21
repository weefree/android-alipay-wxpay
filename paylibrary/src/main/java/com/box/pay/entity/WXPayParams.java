package com.box.pay.entity;

import android.text.TextUtils;

/**
 * Created by zer0 on 2016/12/16.
 */

public class WXPayParams {
    public String return_code;
    public String return_msg;
    public String appid;
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public String timestamp;
    public String packagevalue;
    public String sign;

    public boolean isParamOk(){
        if(TextUtils.isEmpty(this.appid)||
                TextUtils.isEmpty(this.partnerid)||
                TextUtils.isEmpty(this.prepayid)||
                TextUtils.isEmpty(this.noncestr)||
                TextUtils.isEmpty(this.timestamp)||
                TextUtils.isEmpty(this.packagevalue)||
                TextUtils.isEmpty(this.sign)
                ){
            return false;
        }
        return true;
    }
}
