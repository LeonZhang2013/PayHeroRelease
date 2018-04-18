package com.smartpos.payhero;

import android.app.Application;

import com.bill99.smartpos.sdk.api.BillPayment;

/**
 * Created by xudong.zhang on 2017/6/5.
 * Email: xudong.zhang@99bill.com
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //start up sdk
        BillPayment.startUp(this);
        //设置支付SDK调试模式，建议开发版本设为true, 发布版本设为false。默认为false
        BillPayment.setDebugMode(true);

//        //Bugly crash report
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
//        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
//            @Override
//            public synchronized Map<String, String> onCrashHandleStart(int i, String s, String s1, String s2) {
//                // 退出程序
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
//
//                return super.onCrashHandleStart(i, s, s1, s2);
//            }
//
//        });
//        CrashReport.initCrashReport(getApplicationContext(), "d8e8b5fef7", true, strategy);
    }
}
