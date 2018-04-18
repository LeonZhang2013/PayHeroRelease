package com.smartpos.payhero;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smartpos.payhero.databinding.ActivityPaymentResultBinding;

/**
 * Created by xudong.zhang on 2017/6/12.
 * Email: xudong.zhang@99bill.com
 */

public class PaymentResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPaymentResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_result);

        if (getIntent() != null && getIntent().getStringExtra("result") != null) {
            binding.setContent(Utils.formatString(getIntent().getStringExtra("result")));
        } else {
            binding.setContent("支付结果显示页面");
        }
    }

}
