package com.smartpos.payhero.txb.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yy520 on 2018-4-16.
 */

public class Order implements Serializable {

    public static final int ZHIFUBAO = 2;
    public static final int WEIXIN = 3;
    public static final int CASH = 0;
    public static final int CART = 1;

    // 99BILL 快钱钱包B扫C
    // 99BILLCSB 快钱钱包C扫B
    // BAIDU 百度B扫C
    // BAIDUCSB 百度C扫B
    // FFAN 飞凡B扫C
    // FFANCSB 飞凡C扫B
    // ALIPAY 支付宝B扫C
    // ALIPAYCSB 支付宝C扫B
    // CUPBSC 银联B扫C
    // CUPCSB 银联C扫B
    // WECHAT 微信B扫C
    // WECHATCSB 微信C
    public static final String CODE_ZFB = "ALIPAYCSB";
    public static final String CODE_WX = "WECHATCSB";


    private String order_id;
    private String cmd;         //	固定值 3
    private String uid;            //门店操作员ID
    private BigDecimal discount;            //门店ID
    private String discountX10;
    private BigDecimal zkprice;        //折扣部分的价格
    private BigDecimal fzkprice;     //非折扣部分的价格
    private BigDecimal fzkpriceAfter;     //非折扣部分的价格
    private BigDecimal price;        //	总价
    private BigDecimal tprice;        //	实际支付价格
    private int ptype;        //	支付方式 0现金 1刷卡 2支付宝 3微信
    private String phone;        //	电话号码 是会员有，不是会员传空字符串

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDiscount(String discountStr) {
        this.discount = new BigDecimal(discountStr);
        discountX10 = discount.multiply(new BigDecimal(10)).setScale(1, BigDecimal.ROUND_HALF_UP).toString();
        calculationDis();
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setZkprice(String zkprice) {
        if (zkprice.trim().length() == 0) return;

        this.zkprice = new BigDecimal(zkprice);
        calculationDis();
    }

    public void setFzkprice(String fzkprice) {
        if (fzkprice.trim().length() == 0) return;
        this.fzkprice = new BigDecimal(fzkprice);
        calculationDis();
    }

    private void calculationDis() {
        if (zkprice == null) {
            zkprice = new BigDecimal(0);
        }
        if (fzkprice == null) {
            fzkprice = new BigDecimal(0);
        }
        price = zkprice.add(fzkprice);
        if (discount != null) {
            fzkpriceAfter = zkprice.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);
            tprice = fzkpriceAfter.add(fzkprice);
        } else {
            tprice = zkprice.add(fzkprice);
        }
    }


    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 用于显示
     *
     * @return 实际折扣乘以10  （0.95  = 9.5 折）
     */
    public String getDiscountX10() {
        return discountX10;
    }

    public String getCmd() {
        return cmd;
    }


    public String getUid() {
        return uid;
    }


    public BigDecimal getZkprice() {
        return zkprice;
    }


    public BigDecimal getZkpriceAfter() {
        return fzkpriceAfter;
    }

    public BigDecimal getFzkprice() {
        return fzkprice;
    }


    public BigDecimal getTprice() {
        return tprice;
    }

    public String getTpriceX100() {
        return tprice.multiply(new BigDecimal(100)).intValue()+"";
    }


    public int getPtype() {
        return ptype;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
