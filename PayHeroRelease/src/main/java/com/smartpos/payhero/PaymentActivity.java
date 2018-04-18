package com.smartpos.payhero;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smartpos.payhero.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity implements PaymentClickCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPaymentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        binding.setCallback(this);
    }

    @Override
    public void onCpConsumeClick() {
        startActivity(new Intent(PaymentActivity.this, CpConsumeActivity.class));
    }

    @Override
    public void onCpVoidClick() {
        startActivity(new Intent(PaymentActivity.this, CpVoidActivity.class));
    }

    @Override
    public void onCpRefundClick() {
        startActivity(new Intent(PaymentActivity.this, CpRefundActivity.class));
    }

    @Override
    public void onScanBSCClick() {
        startActivity(new Intent(PaymentActivity.this, ScanBSCConsumeActivity.class));
    }

    @Override
    public void onScanCSBClick() {
        startActivity(new Intent(PaymentActivity.this, ScanCSBConsumeActivity.class));
    }

    @Override
    public void onScanVoidClick() {
        startActivity(new Intent(PaymentActivity.this, ScanVoidActivity.class));
    }

    @Override
    public void onScanRefundClick() {
        startActivity(new Intent(PaymentActivity.this, ScanRefundActivity.class));
    }

}
