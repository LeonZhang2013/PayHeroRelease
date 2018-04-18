package com.smartpos.payhero;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.smartpos.payhero.databinding.ActivityMainBinding;
import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MainClickCallback {
    public static final int MY_PERMISSIONS_REQUEST = 0x001;

    boolean isFont = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setCallback(this);
    }

    @Override
    public void onSDKVersionClick() {
        Utils.handleCallback(MainActivity.this, BillPayment.getSDKVersion());
    }

    @Override
    public void onInitClick() {
        if (Build.VERSION.SDK_INT >= 23 &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            permissionRequest();
        } else {
            initPayment();
        }
    }

    @Override
    public void onUpdateParamsClick() {
        BillPayment.updatePaymentParams(this, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Utils.handleCallback(MainActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Utils.handleCallback(MainActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Utils.handleCallback(MainActivity.this, cancelData);
            }
        });
    }

    @Override
    public void onPaymentClick() {
        startActivity(new Intent(MainActivity.this, PaymentActivity.class));
    }

    @Override
    public void onQueryPaymentStatusClick() {
        startActivity(new Intent(MainActivity.this, QueryTransDetailsActivity.class));
    }

    @Override
    public void onQueryTransRecordClick() {
        startActivity(new Intent(MainActivity.this, QueryTransRecordActivity.class));
    }

    @Override
    public void onSummaryTransClick() {
//        BillPayment.summaryTrans(this, new BillPaymentCallback() {
//            @Override
//            public void onSuccess(String successData) {
//                Utils.handleCallback(MainActivity.this, successData);
//            }
//
//            @Override
//            public void onFailed(String failedData) {
//                Utils.handleCallback(MainActivity.this, failedData);
//            }
//
//            @Override
//            public void onCancel(String cancelData) {
//                Utils.handleCallback(MainActivity.this, cancelData);
//            }
//        });
    }

    @Override
    public void onSettlementTransClick() {
        BillPayment.settlementTrans(this, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Utils.handleCallback(MainActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Utils.handleCallback(MainActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Utils.handleCallback(MainActivity.this, cancelData);
            }
        });
    }

    @Override
    public void onPrintClick() {

        //打印信息
        JSONObject content = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            array.put(PrintManager.packerTxtPrintJson("3", "快钱支付清算信息有限公司", "center"));
            array.put(PrintManager.packerTxtPrintJson("3", "www.99bill.com", "center"));
            array.put(PrintManager.packerTxtPrintJson("1", "---", "center"));
            array.put(PrintManager.packerTxtPrintJson("3", "商户：" + "测试商户"));
            array.put(PrintManager.packerTxtPrintJson("2", "商户编号：" + "0201022022"));
            array.put(PrintManager.packerTxtPrintJson("2", "终端编号：" + "92992929292"));
            array.put(PrintManager.packerTxtPrintJson("2", "收单机构：" + "48120000"));
            array.put(PrintManager.packerTxtPrintJson("2", "操作员号：" + "001"));
            array.put(PrintManager.packerTxtPrintJson("1", "---", "center"));
            array.put(PrintManager.packerTxtPrintJson("3", "交\r易：" + "800210709283223"));
            array.put(PrintManager.packerTxtPrintJson("3", "\n"));
            array.put(PrintManager.packerTxtPrintJson("3", "\n"));
            array.put(PrintManager.packerTxtPrintJson("3", "\n"));
            array.put(PrintManager.packerTxtPrintJson("3", "\n"));
            array.put(PrintManager.packerTxtPrintJson("3", "\n"));
            array.put(PrintManager.packerTxtPrintJson("3", "\n"));

            content.put("spos", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bitmap[] bitmaps = new Bitmap[]{};

        BillPayment.print(this, content, bitmaps, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Toast.makeText(MainActivity.this, "Print success! data:" + successData, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String failedData) {
                Toast.makeText(MainActivity.this, "Print failed! cause of:" + failedData, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(String cancelData) {
                Toast.makeText(MainActivity.this, "Print cancel! cancel data:" + cancelData, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStartScannerClick() {
        long timout = 60000;
        isFont = !isFont;

        BillPayment.startScanner(this, timout, isFont ? 1 : 2, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Utils.handleCallback(MainActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Utils.handleCallback(MainActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Utils.handleCallback(MainActivity.this, cancelData);
            }
        });
    }

    @Override
    public void onGetDeviceInfoClick() {
        Utils.handleCallback(MainActivity.this, BillPayment.getDeviceInfo(this).toString());
    }

    @Override
    public void onUpdateBatchNoClick() {
        BillPayment.updateTermBatchNo(this);

        Utils.handleCallback(MainActivity.this, "更新批次号成功");
    }

    @Override
    public void onIndustryCardClick() {
        startActivity(new Intent(MainActivity.this, IndustryCardActivity.class));
    }

    private void initPayment() {
        BillPayment.initPayment(this, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {
                Utils.handleCallback(MainActivity.this, successData);
            }

            @Override
            public void onFailed(String failedData) {
                Utils.handleCallback(MainActivity.this, failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                Utils.handleCallback(MainActivity.this, cancelData);
            }
        });
    }

    private void permissionRequest() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
            //权限拒绝后重新申请权限
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("支付时需要有获取设备号、蓝牙、定位权限")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //申请权限
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                                            Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_REQUEST);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        } else {
            //申请权限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获取权限后进入支付初始化流程
                    initPayment();
                } else {
                    Toast.makeText(MainActivity.this, "没有开启相关权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
