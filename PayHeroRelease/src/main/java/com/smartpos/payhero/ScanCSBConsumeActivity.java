package com.smartpos.payhero;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.smartpos.payhero.databinding.ActivityScanCsbConsumeBinding;
import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.bill99.smartpos.sdk.api.model.BLPaymentRequest;
import com.bill99.smartpos.sdk.api.model.BLScanCSBConsumeMsg;

/**
 * Creator: Kun
 * Date:2017/6/12
 * Email:kun.zhang@99bill.com
 */

public class ScanCSBConsumeActivity extends AppCompatActivity implements NextClickCallback {

    private ActivityScanCsbConsumeBinding mBinding;
    MockData mockData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_csb_consume);

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
        if (!TextUtils.isEmpty(mockData.transId.get()) && !TextUtils.isEmpty(mockData.scanAmt.get()) && !TextUtils.isEmpty(mockData.payType.get())) {
            trade(mockData.transId.get(), mockData.orderId.get(), mockData.merchName.get(),
                    mockData.scanAmt.get(), mockData.payType.get(), mockData.ext1.get(), mockData.ext2.get());
        } else {
            Toast.makeText(this, "参数不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void trade(String transId, String orderId, String merchantName, String amt, String payType, String ext1, String ext2) {

        //封装请求消息
        BLScanCSBConsumeMsg sdkMsg = new BLScanCSBConsumeMsg();
        //支付交易号
        sdkMsg.transId = transId;
        //订单号
        sdkMsg.orderId = orderId;
        //商品名称
        sdkMsg.merchName = merchantName;
        //金额
        sdkMsg.amt = amt;
        //收款方式
        sdkMsg.payType = payType;
        //附加字段一
        sdkMsg.ext1 = ext1;
        //附加字段二
        sdkMsg.ext2 = ext2;

        sdkMsg.cur = mockData.cur.get();


        System.out.println(sdkMsg.toString());
        final BLPaymentRequest<BLScanCSBConsumeMsg> params = new BLPaymentRequest<>();
        params.data = sdkMsg;

        BillPayment.startPayment(ScanCSBConsumeActivity.this, params, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Log.i("callback", "onSuccess");
                Log.i("response", successData);

                Utils.handleCallback(ScanCSBConsumeActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Log.i("callback", "onFailed");
                Log.i("response", failedData);

                Utils.handleCallback(ScanCSBConsumeActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Log.i("callback", "onCancel");
                Log.i("dealType", cancelData);

                Utils.handleCallback(ScanCSBConsumeActivity.this, cancelData);
            }
        });
    }

}
