package com.smartpos.payhero.txb.bean;

import java.io.Serializable;

/**
 * Created by yy520 on 2018-4-2.
 */

public class User implements Serializable {

    private String uid;
    private String username;
    private String discount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", discount=" + discount +
                '}';
    }
}
