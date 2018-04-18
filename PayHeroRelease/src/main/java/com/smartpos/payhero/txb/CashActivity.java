package com.smartpos.payhero.txb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartpos.payhero.PrintManager;
import com.smartpos.payhero.R;
import com.smartpos.payhero.txb.tools.PayTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import butterknife.BindView;

public class CashActivity extends BaseActivity {

    PayTools payTools;

    @BindView(R.id.yinshou)
    TextView yinshou;

    @BindView(R.id.shishou)
    EditText shishou;


    @BindView(R.id.zhaolin_btn)
    Button zhaolin;

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.cash_layout);
        payTools = new PayTools(this);
        initView();
    }

    public void initView(){
        yinshou.setText(Constant.getOrder().getTprice().toString());
    }

    public void zhaolinClick(View v){
        String amount = shishou.getText().toString();
        if(amount.trim().length()==0){
            return;
        }
        BigDecimal a = new BigDecimal(amount);
        BigDecimal subtract = a.subtract(Constant.getOrder().getTprice());
        zhaolin.setText("找零："+subtract.toString()+"元");
    }


    public void commit(View v){
        String amount = shishou.getText().toString();
        if(amount.trim().length()==0){
            return;
        }
        BigDecimal a = new BigDecimal(amount);
        BigDecimal subtract = a.subtract(Constant.getOrder().getTprice());

        //打印信息
        final JSONObject content = new JSONObject();
        JSONArray array = new JSONArray();
        try {




            array.put(PrintManager.packerTxtPrintJson("3", "快钱支付清算信息有限公司", "center"));
            array.put(PrintManager.packerTxtPrintJson("3", "www.99bill.com", "center"));
            array.put(PrintManager.packerTxtPrintJson("1", "---", "center"));
            array.put(PrintManager.packerTxtPrintJson("3", "商户：" + Constant.getUser().getUsername()));
            array.put(PrintManager.packerTxtPrintJson("2", "商户编号：" + "0201022022"));
            array.put(PrintManager.packerTxtPrintJson("2", "终端编号：" + "92992929292"));
            array.put(PrintManager.packerTxtPrintJson("2", "收单机构：" + "48120000"));
            array.put(PrintManager.packerTxtPrintJson("2", "操作员号：" + Constant.getUser().getUid()));
            array.put(PrintManager.packerTxtPrintJson("1", "---", "center"));
            array.put(PrintManager.packerTxtPrintJson("3", "交\r易：" + Constant.getOrder().getOrder_id()));
            array.put(PrintManager.packerTxtPrintJson("2", "交易方式：现金" ));
            array.put(PrintManager.packerTxtPrintJson("2", "应收：" + Constant.getOrder().getTprice()+"元"));
            array.put(PrintManager.packerTxtPrintJson("2", "实收：" + amount+"元"));
            array.put(PrintManager.packerTxtPrintJson("2", "找零：" + subtract+"元"));
            array.put(PrintManager.packerTxtPrintJson("3", "\n"));
            array.put(PrintManager.packerTxtPrintJson("3", "\n"));
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
        payTools.printTicket(content);
        payTools.sendMessage();
    }


}
