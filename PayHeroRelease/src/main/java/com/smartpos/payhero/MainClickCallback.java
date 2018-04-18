package com.smartpos.payhero;


/**
 * Created by xudong.zhang on 2017/6/5.
 * Email: xudong.zhang@99bill.com
 */

public interface MainClickCallback {
    void onSDKVersionClick();

    void onInitClick();

    void onUpdateParamsClick();

    void onPaymentClick();

    void onQueryPaymentStatusClick();

    void onQueryTransRecordClick();

    void onSummaryTransClick();

    void onSettlementTransClick();

    void onPrintClick();

    void onStartScannerClick();

    void onGetDeviceInfoClick();

    void onUpdateBatchNoClick();

    void onIndustryCardClick();
}
