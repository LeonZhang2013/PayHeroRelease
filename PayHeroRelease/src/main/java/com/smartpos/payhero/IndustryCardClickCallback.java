package com.smartpos.payhero;

/**
 * Created by xudong.zhang on 2017/6/5.
 * Email: xudong.zhang@99bill.com
 */

public interface IndustryCardClickCallback {
    void onOpenNCClick();

    void onCloseNCClick();

    void onIsExistNCClick();

    void onApduCommNCClick();

    void onHaltNCClick();

    void onrResetNCClick();

    void onGetCardCodeNCClick();

    void onGetCardTypeNCClick();

    void onAuthNCClick();

    void onReadBlockNCClick();

    void onWriteBlockNCClick();

    void onAddValueNCClick();

    void onReduceValueNCClick();
}
