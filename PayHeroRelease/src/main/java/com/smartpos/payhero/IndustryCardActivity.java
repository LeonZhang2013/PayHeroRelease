package com.smartpos.payhero;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smartpos.payhero.databinding.ActivityIndustryCardBinding;
import com.bill99.smartpos.porting.SPOSException;
import com.bill99.smartpos.sdk.api.BillIndustryCard;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.bill99.smartpos.sdk.api.model.BLIndustryCardType;

public class IndustryCardActivity extends AppCompatActivity implements IndustryCardClickCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityIndustryCardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_industry_card);
        binding.setCallback(this);
    }

    @Override
    public void onOpenNCClick() {
        Utils.handleCallback(IndustryCardActivity.this, String.valueOf(BillIndustryCard.openNC(this)));
    }

    @Override
    public void onCloseNCClick() {
        Utils.handleCallback(IndustryCardActivity.this, String.valueOf(BillIndustryCard.closeNC()));
    }

    @Override
    public void onIsExistNCClick() {
        Utils.handleCallback(IndustryCardActivity.this, String.valueOf(BillIndustryCard.isExistNC()));
    }

    @Override
    public void onApduCommNCClick() {
        try {
            Utils.handleCallback(IndustryCardActivity.this, ISOUtils.hexString(BillIndustryCard.apduCommNC(
                    ISOUtils.hex2byte("0084000004")) != null ? BillIndustryCard.apduCommNC(ISOUtils.hex2byte("0084000004")) : "null".getBytes()));
        } catch (SPOSException e) {
            e.printStackTrace();
            Utils.handleCallback(IndustryCardActivity.this, "failed, cause of" + e.resCode + ":" + e.resMsg);
        }
    }

    @Override
    public void onHaltNCClick() {
        try {
            BillIndustryCard.haltNC();
            Utils.handleCallback(IndustryCardActivity.this, "success");
        } catch (SPOSException e) {
            e.printStackTrace();
            Utils.handleCallback(IndustryCardActivity.this, "failed, cause of" + e.resCode + ":" + e.resMsg);
        }
    }

    @Override
    public void onrResetNCClick() {
        BillIndustryCard.resetNC(BLIndustryCardType.NC_MIFARE_ONE, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Utils.handleCallback(IndustryCardActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Utils.handleCallback(IndustryCardActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {

            }
        });
    }

    String cardCode = "";

    @Override
    public void onGetCardCodeNCClick() {
        try {
            cardCode = ISOUtils.hexString(BillIndustryCard.getCardCodeNC());
            Utils.handleCallback(IndustryCardActivity.this, cardCode);
        } catch (SPOSException e) {
            e.printStackTrace();
            Utils.handleCallback(IndustryCardActivity.this, "failed, cause of" + e.resCode + ":" + e.resMsg);
        }
    }

    @Override
    public void onGetCardTypeNCClick() {
        BillIndustryCard.getCardTypeNC(new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Utils.handleCallback(IndustryCardActivity.this, String.valueOf(successData));
            }

            @Override
            public void onFailed(String failedData) {
                Utils.handleCallback(IndustryCardActivity.this, String.valueOf(failedData));
            }

            @Override
            public void onCancel(String cancelData) {

            }
        });
    }

    @Override
    public void onAuthNCClick() {
        try {
            BillIndustryCard.authNC(0X00, (byte) 0x01, ISOUtils.hex2byte("FFFFFFFFFFFF"), ISOUtils.hex2byte(cardCode));
            Utils.handleCallback(IndustryCardActivity.this, "success");
        } catch (SPOSException e) {
            e.printStackTrace();
            Utils.handleCallback(IndustryCardActivity.this, "failed, cause of" + e.resCode + ":" + e.resMsg);
        }
    }

    @Override
    public void onReadBlockNCClick() {
        try {
            Utils.handleCallback(IndustryCardActivity.this, ISOUtils.hexString(BillIndustryCard.readBlockNC((byte) 0x01)));
        } catch (SPOSException e) {
            e.printStackTrace();
            Utils.handleCallback(IndustryCardActivity.this, "failed, cause of" + e.resCode + ":" + e.resMsg);
        }
    }

    @Override
    public void onWriteBlockNCClick() {
        try {
            BillIndustryCard.writeBlockNC((byte) 0x01, ISOUtils.hex2byte("01000000FEFFFFFF0100000001FE01FE"));
            Utils.handleCallback(IndustryCardActivity.this, "success");
        } catch (SPOSException e) {
            e.printStackTrace();
            Utils.handleCallback(IndustryCardActivity.this, "failed, cause of" + e.resCode + ":" + e.resMsg);
        }
    }

    @Override
    public void onAddValueNCClick() {
        try {
            BillIndustryCard.addValueNC((byte) 0x01, ISOUtils.hex2byte("01000000"));
            Utils.handleCallback(IndustryCardActivity.this, "success");
        } catch (SPOSException e) {
            e.printStackTrace();
            Utils.handleCallback(IndustryCardActivity.this, "failed, cause of" + e.resCode + ":" + e.resMsg);
        }
    }

    @Override
    public void onReduceValueNCClick() {
        try {
            BillIndustryCard.reduceValueNC((byte) 0x01, ISOUtils.hex2byte("01000000"));
            Utils.handleCallback(IndustryCardActivity.this, "success");
        } catch (SPOSException e) {
            e.printStackTrace();
            Utils.handleCallback(IndustryCardActivity.this, "failed, cause of" + e.resCode + ":" + e.resMsg);
        }
    }
}
