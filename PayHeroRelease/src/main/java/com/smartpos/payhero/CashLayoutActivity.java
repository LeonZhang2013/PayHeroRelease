package com.smartpos.payhero;

import android.os.Bundle;
import android.view.View;

import com.smartpos.payhero.txb.BaseActivity;
import com.smartpos.payhero.txb.Constant;
import com.smartpos.payhero.txb.bean.Order;
import com.smartpos.payhero.txb.tools.PayTools;

public class CashLayoutActivity extends BaseActivity {

    PayTools payTools;
    PayTools.OrderCallback orderCallback;


    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.pay_code_layout);
        payTools = new PayTools(this);
    }

    public void zhifubao(View v){
        Constant.getOrder().setPtype(Order.ZHIFUBAO);
        payTools.generateOrder(Constant.getOrder(),new PayTools.OrderCallback() {
            @Override
            public void handler(Order order) {
                payTools.startPayCoder(order,Order.CODE_ZFB);
            }
        });
    }

    public void weixin(View v){
        Constant.getOrder().setPtype(Order.WEIXIN);
        payTools.generateOrder(Constant.getOrder(),new PayTools.OrderCallback() {
            @Override
            public void handler(Order order) {
                payTools.startPayCoder(order,Order.CODE_WX);
            }
        });
    }

}
