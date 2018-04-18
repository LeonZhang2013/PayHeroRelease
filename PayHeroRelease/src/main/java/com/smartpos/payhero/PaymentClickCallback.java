package com.smartpos.payhero;

/**
 * Created by xudong.zhang on 2017/6/5.
 * Email: xudong.zhang@99bill.com
 */

public interface PaymentClickCallback {
    void onCpConsumeClick();

    void onCpVoidClick();

    void onCpRefundClick();

    void onScanBSCClick();

    void onScanCSBClick();

    void onScanVoidClick();

    void onScanRefundClick();
}
