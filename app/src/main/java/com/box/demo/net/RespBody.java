package com.box.demo.net;

import com.box.pay.entity.WXPayParams;

/**
 * Created by zer0 on 2016/12/20.
 */

public class RespBody {
    public static class WXPayParamsResp{
        public int code;
        public String msg;
        public WXPayParams data;
    }
}
