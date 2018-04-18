package com.smartpos.payhero;

import android.databinding.ObservableField;
import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xudong.zhang on 2017/6/14.
 * Email: xudong.zhang@99bill.com
 */

public class MockData {
    public ObservableField<String> transId = new ObservableField<>("");
    public ObservableField<String> orderId = new ObservableField<>("");
    public ObservableField<String> refId = new ObservableField<>("");
    public ObservableField<String> originalIdTxn = new ObservableField<>("");
    public ObservableField<String> merchName = new ObservableField<>("测试商品");
    public ObservableField<String> amt = new ObservableField<>("3000");
    public ObservableField<String> scanAmt = new ObservableField<>("70100");
    public ObservableField<String> txnId = new ObservableField<>("");
    public ObservableField<String> transType = new ObservableField<>("1");//1有卡，2扫码
    public ObservableField<String> idCardNo = new ObservableField<>("320502196911021526");
    ObservableField<String> cur = new ObservableField<>("CNY");
    public ObservableField<String> payType = new ObservableField<>("WECHATCSB");//99BILLCSB,WECHATCSB,ALIPAYCSB,FFANCSB,CUPCSB
    public ObservableField<String> ext1 = new ObservableField<>(Base64.encodeToString("ext1 param".getBytes(), Base64.DEFAULT));
    public ObservableField<String> ext2 = new ObservableField<>(Base64.encodeToString("ext2 param".getBytes(), Base64.DEFAULT));

    public ObservableField<String> startTime = new ObservableField<>(getStartTime(new Date()));
    public ObservableField<String> endTime = new ObservableField<>(getEndTime(new Date()));
    public ObservableField<String> pageIndex = new ObservableField<>("1");
    public ObservableField<String> pageSize = new ObservableField<>("10");

    private String getEndTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return sdf.format(date);
    }

    private String getStartTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, -5);
        Date date1 = calendar.getTime();
        return sdf.format(date1);
    }

}
