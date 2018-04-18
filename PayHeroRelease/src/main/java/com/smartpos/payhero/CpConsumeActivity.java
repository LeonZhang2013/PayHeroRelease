package com.smartpos.payhero;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.smartpos.payhero.MockData;
import com.smartpos.payhero.NextClickCallback;
import com.smartpos.payhero.Utils;
import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.bill99.smartpos.sdk.api.model.BLCPConsumeMsg;
import com.bill99.smartpos.sdk.api.model.BLPaymentRequest;
import com.smartpos.payhero.databinding.ActivityCpConsumeBinding;

/**
 * Creator: Kun
 * Date:2017/6/12
 * Email:kun.zhang@99bill.com
 * <p>
 * 有卡消费
 */

public class CpConsumeActivity extends AppCompatActivity implements NextClickCallback {

    private ActivityCpConsumeBinding mBinding;
    MockData mockData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cp_consume);

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
        if (!TextUtils.isEmpty(mockData.transId.get()) && !TextUtils.isEmpty(mockData.amt.get())) {
            doCPConsume(mockData.transId.get(), mockData.orderId.get(), mockData.merchName.get(),
                    mockData.amt.get(), mockData.ext1.get(), mockData.ext2.get());
        } else {
            Toast.makeText(this, "参数不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void doCPConsume(String transId, String orderId, String merchantName, String amt, String ext1, String ext2) {

        //封装请求消息
        BLCPConsumeMsg sdkMsg = new BLCPConsumeMsg();
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

        //传入参数
        final BLPaymentRequest<BLCPConsumeMsg> params = new BLPaymentRequest<>();
        params.data = sdkMsg;

        BillPayment.startPayment(CpConsumeActivity.this, params, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Log.i("callback", "onSuccess");
                Log.i("response", successData);

                Utils.handleCallback(CpConsumeActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Log.i("callback", "onFailed");
                Log.i("response", failedData);

                Utils.handleCallback(CpConsumeActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Log.i("callback", "onCancel");
                Log.i("dealType", cancelData);

                Utils.handleCallback(CpConsumeActivity.this, cancelData);
            }
        });

//        //子线程, 异步转同步
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final CountDownLatch latch = new CountDownLatch(1);
//
//                BillPayment.startPayment(CpConsumeActivity.this, params, new BillPaymentCallback() {
//                    @Override
//                    public void onSuccess(String successData) {
//                        Log.i("callback", "onSuccess");
//                        Log.i("response", successData);
//
//                        latch.countDown();
//                        Utils.handleCallback(CpConsumeActivity.this, successData);
//                    }
//
//                    @Override
//                    public void onFailed(String failedData) {
//                        Log.i("callback", "onFailed");
//                        Log.i("response", failedData);
//
//                        latch.countDown();
//                        Utils.handleCallback(CpConsumeActivity.this, failedData);
//                    }
//
//                    @Override
//                    public void onCancel(String cancelData) {
//                        Log.i("callback", "onCancel");
//                        Log.i("dealType", cancelData);
//
//                        Utils.handleCallback(CpConsumeActivity.this, cancelData);
//                        latch.countDown();
//                    }
//                });
//
//                try {
//                    latch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
    }

}
