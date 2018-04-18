package com.smartpos.payhero.txb;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.RadioGroup;

import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.smartpos.payhero.R;

import butterknife.BindView;

import static com.smartpos.payhero.MainActivity.MY_PERMISSIONS_REQUEST;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private Fragment receivableF,statisticsF;
    @BindView(R.id.rd_group)
    public RadioGroup mMenu;


    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.txb_activity_main);
        initView();
        initPayment();
    }

    public void initView() {
        mMenu.setOnCheckedChangeListener(this);
        mMenu.check(R.id.menu_receive);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (checkedId){
            case R.id.menu_receive:
                if(receivableF==null){
                    receivableF = new ReceivableFragment();
                    transaction.add(R.id.fragment_container,receivableF);
                }else{
                    transaction.show(receivableF);
                }
                break;
            case R.id.menu_statistics:
                if(statisticsF==null){
                    statisticsF = new StatisticsFragment();
                    transaction.add(R.id.fragment_container,statisticsF);
                }else{
                    transaction.show(statisticsF);
                }
                break;
        }
        transaction.commit();
    }

    public void hideAllFragment(FragmentTransaction transaction){
        if(receivableF!=null){
            transaction.hide(receivableF);
        }
        if(statisticsF!=null){
            transaction.hide(statisticsF);
        }
    }


    private void initPayment() {
        if (Build.VERSION.SDK_INT >= 23 &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            permissionRequest();
        } else {
            BillPayment.initPayment(this, new BillPaymentCallback() {
                @Override
                public void onSuccess(String successData) {}

                @Override
                public void onFailed(String failedData) {
                    showToast("初始化失败");
                }

                @Override
                public void onCancel(String cancelData) {
                    showToast("初始化取消");
                }
            });
        }

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


    boolean isExit = false;
    @Override
    public void onBackPressed() {
        if (!isExit) {
            showToast("再次点击退出");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        isExit = true;
                        Thread.sleep(2000);
                        isExit = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return;
        }
        super.onBackPressed();
    }
}
