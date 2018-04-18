package com.smartpos.payhero.txb;

import android.app.Application;

import com.bill99.smartpos.sdk.api.BillPayment;

/**
 * Created by yy520 on 2018-4-9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //start up sdk
        BillPayment.startUp(this);
        //设置支付SDK调试模式，建议开发版本设为true, 发布版本设为false。默认为false
        BillPayment.setDebugMode(true);

    }




}
