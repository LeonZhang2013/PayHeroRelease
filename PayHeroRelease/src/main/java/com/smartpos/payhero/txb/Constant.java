package com.smartpos.payhero.txb;


import com.smartpos.payhero.txb.bean.Order;
import com.smartpos.payhero.txb.bean.User;

/**
 * Created by yy520 on 2018-4-9.
 */

public class Constant {

    private static User user;

    private static Order order;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Constant.user = user;
    }

    public static Order getOrder() {
        return order;
    }

    public static void setOrder(Order order) {
        Constant.order = order;
    }
}
