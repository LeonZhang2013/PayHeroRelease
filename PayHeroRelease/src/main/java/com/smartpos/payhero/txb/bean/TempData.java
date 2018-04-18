package com.smartpos.payhero.txb.bean;

import java.io.Serializable;

/**
 * Created by yy520 on 2018-4-9.
 */

public class TempData implements Serializable {

    int status;
    String order_id;
    float discount;
    String phone;
    String zkprice;
    String fzkprice;
    String price;
    String ptype;
    String tprice;
    String time;


    public String getZkprice() {
        return zkprice;
    }

    public void setZkprice(String zkprice) {
        this.zkprice = zkprice;
    }

    public String getFzkprice() {
        return fzkprice;
    }

    public void setFzkprice(String fzkprice) {
        this.fzkprice = fzkprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
