package com.box.pay.impl;

/**
 * Created by zer0 on 2016/12/16.
 */

public interface OnPayFinishListener {
    void onSuccess();
    void onFail(String code);
    void onCancel();
}
