package com.smartpos.payhero;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.smartpos.payhero.databinding.ActivityScanRefundBinding;
import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.bill99.smartpos.sdk.api.model.BLPaymentRequest;
import com.bill99.smartpos.sdk.api.model.BLScanRefundMsg;

/**
 * Creator: Kun
 * Date:2017/6/12
 * Email:kun.zhang@99bill.com
 * <p>
 * 扫码退货
 */

public class ScanRefundActivity extends AppCompatActivity implements NextClickCallback {

    private ActivityScanRefundBinding mBinding;
    MockData mockData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_refund);

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
        if (!TextUtils.isEmpty(mockData.transId.get()) && !TextUtils.isEmpty(mockData.scanAmt.get()) && !TextUtils.isEmpty(mockData.originalIdTxn.get())) {
            trade(mockData.transId.get(), mockData.orderId.get(), mockData.scanAmt.get(),
                    mockData.originalIdTxn.get(), mockData.ext1.get(), mockData.ext2.get());
        } else {
            Toast.makeText(this, "参数不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void trade(String transId, String orderId, String amt, String originalIdTxn, String ext1, String ext2) {

        //封装请求消息
        BLScanRefundMsg sdkMsg = new BLScanRefundMsg();
        //支付交易号
        sdkMsg.transId = transId;
        //订单号
        sdkMsg.orderId = orderId;
        //金额
        sdkMsg.amt = amt;
        //原交易编号
        sdkMsg.originalIdTxn = originalIdTxn;
        //附加字段一
        sdkMsg.ext1 = ext1;
        //附加字段二
        sdkMsg.ext2 = ext2;

        final BLPaymentRequest<BLScanRefundMsg> params = new BLPaymentRequest<>();
        params.data = sdkMsg;

        BillPayment.startPayment(ScanRefundActivity.this, params, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Log.i("callback", "onSuccess");
                Log.i("response", successData);

                Utils.handleCallback(ScanRefundActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Log.i("callback", "onFailed");
                Log.i("response", failedData);

                Utils.handleCallback(ScanRefundActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Log.i("callback", "onCancel");
                Log.i("dealType", cancelData);

                Utils.handleCallback(ScanRefundActivity.this, cancelData);
            }
        });
    }

}
