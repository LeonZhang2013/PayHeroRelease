package com.smartpos.payhero;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.smartpos.payhero.databinding.ActivityQueryTransDetailsBinding;
import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.bill99.smartpos.sdk.api.model.BLQueryTransDetailsMsg;

/**
 * Creator: Kun
 * Date:2017/6/12
 * Email:kun.zhang@99bill.com
 */

public class QueryTransDetailsActivity extends AppCompatActivity implements NextClickCallback {

    private ActivityQueryTransDetailsBinding mBinding;
    MockData mockData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_query_trans_details);

        mBinding.setCallback(this);
        mockData = new MockData();
        mBinding.setMock(mockData);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onNextClick() {
        if ((TextUtils.isEmpty(mockData.transId.get()) && TextUtils.isEmpty(mockData.refId.get())) || TextUtils.isEmpty(mockData.transType.get())) {
            Toast.makeText(this, "参数不能为空", Toast.LENGTH_SHORT).show();
        } else {
            query(mockData.transId.get(), mockData.refId.get(),mockData.txnId.get(),mockData.transType.get());
        }
    }

    private void query(String transId, String refId, String txnId, String transType) {
        //传入参数
        BLQueryTransDetailsMsg params = new BLQueryTransDetailsMsg();
        //参数属性
        params.transId = transId;
        params.refId = refId;
        params.txnId = txnId;
        params.transType = transType;


        BillPayment.queryTransDetails(this, params, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Utils.handleCallback(QueryTransDetailsActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Utils.handleCallback(QueryTransDetailsActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Utils.handleCallback(QueryTransDetailsActivity.this, cancelData);
            }
        });
    }

}
