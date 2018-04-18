package com.smartpos.payhero.txb;

import android.os.Bundle;
import android.view.View;

import com.smartpos.payhero.R;
import com.smartpos.payhero.txb.bean.Order;
import com.smartpos.payhero.txb.tools.PayTools;

public class PayMethdActivity extends BaseActivity {

    PayTools payTools;
    PayTools.OrderCallback callback;

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.pay_methd_layout);
        payTools = new PayTools(this);

        callback = new PayTools.OrderCallback() {
            @Override
            public void handler(Order order) {
                payTools.startPay(order);
            }
        };
    }



    public void zhifubao(View v){
        Constant.getOrder().setPtype(Order.ZHIFUBAO);
        payTools.generateOrder(Constant.getOrder(),callback);
    }

    public void weixin(View v){
        Constant.getOrder().setPtype(Order.WEIXIN);
        payTools.generateOrder(Constant.getOrder(),callback);
    }

    public void cash(View v){
        Constant.getOrder().setPtype(Order.CASH);
        payTools.generateOrder(Constant.getOrder(), new PayTools.OrderCallback() {
            @Override
            public void handler(Order order) {
                startActivity(CashActivity.class);
            }
        });
    }

    public void cart(View v){
        Constant.getOrder().setPtype(Order.CART);
        payTools.generateOrder(Constant.getOrder(),callback);
    }
}
