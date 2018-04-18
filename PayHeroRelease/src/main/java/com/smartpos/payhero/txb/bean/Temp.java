package com.smartpos.payhero.txb.bean;

import java.io.Serializable;

/**
 * Created by yy520 on 2018-4-14.
 */

public class Temp implements Serializable {

    private int error_code;
    private String msg;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
