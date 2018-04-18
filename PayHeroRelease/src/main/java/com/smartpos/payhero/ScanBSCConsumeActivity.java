package com.smartpos.payhero;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.smartpos.payhero.databinding.ActivityScanBscConsumeBinding;
import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.bill99.smartpos.sdk.api.model.BLPaymentRequest;
import com.bill99.smartpos.sdk.api.model.BLScanBSCConsumeMsg;

/**
 * Creator: Kun
 * Date:2017/6/12
 * Email:kun.zhang@99bill.com
 */

public class ScanBSCConsumeActivity extends AppCompatActivity implements NextClickCallback {

    private ActivityScanBscConsumeBinding mBinding;
    MockData mockData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_bsc_consume);

        mBinding.setCallback(this);
        mockData = new MockData();
        mBinding.setMock(mockData);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mockData.transId.set(Utils.generateOrderId());
        mockData.orderId.set(Utils.generateOrderId());
    }

    @Override
    public void onNextClick() {
        if (!TextUtils.isEmpty(mockData.transId.get()) && !TextUtils.isEmpty(mockData.scanAmt.get())) {
            trade(mockData.transId.get(), mockData.orderId.get(), mockData.merchName.get(),
                    mockData.scanAmt.get(), mockData.ext1.get(), mockData.ext2.get());
        } else {
            Toast.makeText(this, "参数不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void trade(String transId, String orderId, String merchantName, String amt, String ext1, String ext2) {

        //封装请求消息
        BLScanBSCConsumeMsg sdkMsg = new BLScanBSCConsumeMsg();
        //支付交易号
        sdkMsg.transId = transId;
        //订单号
        sdkMsg.orderId = orderId;
        //商品名称
        sdkMsg.merchName = merchantName;
        //金额
        sdkMsg.amt = amt;
        //附加字段一
        sdkMsg.ext1 = ext1;
        //附加字段二
        sdkMsg.ext2 = ext2;

        final BLPaymentRequest<BLScanBSCConsumeMsg> params = new BLPaymentRequest<>();
        params.data = sdkMsg;

        BillPayment.startPayment(ScanBSCConsumeActivity.this, params, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Log.i("callback", "onSuccess");
                Log.i("response", successData);

                Utils.handleCallback(ScanBSCConsumeActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Log.i("callback", "onFailed");
                Log.i("response", failedData);

                Utils.handleCallback(ScanBSCConsumeActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Log.i("callback", "onCancel");
                Log.i("dealType", cancelData);

                Utils.handleCallback(ScanBSCConsumeActivity.this, cancelData);
            }
        });
    }

}
