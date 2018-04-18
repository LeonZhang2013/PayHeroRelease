package com.smartpos.payhero;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.smartpos.payhero.databinding.ActivityQueryTransRecordBinding;
import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.bill99.smartpos.sdk.api.model.BLQueryTransRecordMsg;

/**
 * Creator: Kun
 * Date:2017/6/12
 * Email:kun.zhang@99bill.com
 */

public class QueryTransRecordActivity extends AppCompatActivity implements NextClickCallback {

    private ActivityQueryTransRecordBinding mBinding;
    MockData mockData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_query_trans_record);

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
        if (!TextUtils.isEmpty(mockData.startTime.get()) && !TextUtils.isEmpty(mockData.endTime.get()) &&
                !TextUtils.isEmpty(mockData.pageIndex.get()) && !TextUtils.isEmpty(mockData.pageSize.get())) {
            query(mockData.startTime.get(), mockData.endTime.get(), mockData.pageIndex.get(), mockData.pageSize.get());
        } else {
            Toast.makeText(this, "参数不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void query(String startTime, String endTime, String pageIndex, String pageSize) {
        //传入参数
        BLQueryTransRecordMsg params = new BLQueryTransRecordMsg();
        //参数属性
        params.startTime = startTime;
        params.endTime = endTime;
        params.pageIndex = pageIndex;
        params.pageSize = pageSize;

        BillPayment.queryTransRecord(this, params, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Utils.handleCallback(QueryTransRecordActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Utils.handleCallback(QueryTransRecordActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Utils.handleCallback(QueryTransRecordActivity.this, cancelData);
            }
        });
    }

}
