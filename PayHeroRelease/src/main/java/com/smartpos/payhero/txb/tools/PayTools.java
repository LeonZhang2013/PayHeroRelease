package com.smartpos.payhero.txb.tools;

import android.databinding.ObservableField;
import android.graphics.Bitmap;

import com.bill99.smartpos.sdk.api.BillPayment;
import com.bill99.smartpos.sdk.api.BillPaymentCallback;
import com.bill99.smartpos.sdk.api.model.BLCPConsumeMsg;
import com.bill99.smartpos.sdk.api.model.BLPaymentRequest;
import com.bill99.smartpos.sdk.api.model.BLScanBSCConsumeMsg;
import com.bill99.smartpos.sdk.api.model.BLScanCSBConsumeMsg;
import com.google.gson.Gson;
import com.smartpos.payhero.txb.BaseActivity;
import com.smartpos.payhero.txb.bean.Order;
import com.smartpos.payhero.txb.bean.Temp;
import com.smartpos.payhero.txb.bean.TempData;
import com.smartpos.payhero.txb.net.ApiService;
import com.smartpos.payhero.txb.net.NetTools;
import com.smartpos.payhero.txb.net.ProcessObserver;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.smartpos.payhero.txb.Constant.getOrder;

/**
 * Created by yy520 on 2018-4-16.
 */

public class PayTools {

    private BaseActivity context;


    public PayTools(BaseActivity context) {
        this.context = context;
    }

    public interface OrderCallback {
        void handler(Order order);
    }

    public void generateOrder(final Order order, final OrderCallback orderCallback) {

//        cmd	是	int	固定值 3
//        uid	是	string	门店ID
//        zkprice	是	float	折扣部分的价格
//        fzkprice	是	float	非折扣部分的价格
//        price	是	float	总价
//        tprice	是	float	实际支付价格
//        ptype	是	int	支付方式 0现金 1刷卡 2支付宝 3微信
//        phone	否	string	电话号码 是会员有，不是会员传空字符串

        JSONObject user = new JSONObject();
        try {
            user.put("cmd", 3);
            user.put("uid", order.getUid());
            user.put("zkprice", order.getZkprice());
            user.put("fzkprice", order.getFzkprice());
            user.put("price", order.getPrice());
            user.put("tprice", order.getTprice());
            user.put("ptype", order.getPtype());
            user.put("phone", order.getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), user.toString());
        ApiService service = NetTools.createService(ApiService.class);
        service.login(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProcessObserver<Response<ResponseBody>>(context) {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> response) {
                        try {
                            Gson gson = new Gson();
                            Temp temp = gson.fromJson(response.body().string(), Temp.class);
                            if(temp.getError_code()==0){
                                context.showToast(temp.getMsg());
                            }
                            TempData data = gson.fromJson(temp.getData().toString(), TempData.class);
                            getOrder().setOrder_id(data.getOrder_id());
                            orderCallback.handler(getOrder());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void startPayCoder(Order order, String code) {
        //封装请求消息
        BLScanCSBConsumeMsg sdkMsg = new BLScanCSBConsumeMsg();
        //支付交易号
        sdkMsg.transId = order.getOrder_id();
        //订单号
        sdkMsg.orderId = order.getOrder_id();
        //金额
        sdkMsg.amt = order.getTpriceX100();
        //收款方式
        sdkMsg.payType = code;

        sdkMsg.cur = new ObservableField<>("CNY").get(); //外包使用

        final BLPaymentRequest<BLScanCSBConsumeMsg> params = new BLPaymentRequest<>();
        params.data = sdkMsg;
        startPayment(params);
    }

    public void startPay(Order order) {
        BLPaymentRequest params = new BLPaymentRequest<>();
        if (order.getPtype() == Order.CASH) {


        } else if (order.getPtype() == Order.CART) {
            BLCPConsumeMsg sdkMsg = new BLCPConsumeMsg();
            sdkMsg.transId = order.getOrder_id();
            sdkMsg.orderId = order.getOrder_id();
            sdkMsg.amt = order.getTpriceX100();
            params.data = sdkMsg;
        } else {
            BLScanBSCConsumeMsg sdkMsg = new BLScanBSCConsumeMsg();
            sdkMsg.transId = order.getOrder_id();
            sdkMsg.orderId = order.getOrder_id();
            sdkMsg.amt = order.getTpriceX100();
            params.data = sdkMsg;
        }
        startPayment(params);
    }

    public void startPayment(BLPaymentRequest<BLScanCSBConsumeMsg> params){
        BillPayment.startPayment(context, params, new BillPaymentCallback() {

            @Override
            public void onSuccess(String successData) {
                sendMessage();
            }

            @Override
            public void onFailed(String failedData) {
                priseShow(failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                priseShow(cancelData);
            }
        });

    }

    public void printTicket(final JSONObject content) {
        Bitmap[] bitmaps = new Bitmap[]{};
        BillPayment.print(context, content, bitmaps, new BillPaymentCallback() {
            @Override
            public void onSuccess(String successData) {

            }

            @Override
            public void onFailed(String failedData) {
                priseShow(failedData);
            }

            @Override
            public void onCancel(String cancelData) {
                priseShow(cancelData);
            }
        });
    }

    public void priseShow(String content){
        try {
            JSONObject jsonObject = new JSONObject(content);
            Object msg = jsonObject.get("msg");
            context.showToast(msg.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据到服务器。
     */
    public void sendMessage() {
//        cmd	是	int	固定值 6
//        uid	是	string	门店ID
//        order_id	是	string	订单编号ID
        JSONObject user = new JSONObject();
        try {
            user.put("cmd", 6);
            user.put("uid", getOrder().getUid());
            user.put("order_id", getOrder().getOrder_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), user.toString());
        ApiService service = NetTools.createService(ApiService.class);
        service.login(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProcessObserver<Response<ResponseBody>>(context) {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> response) {
                        try {
                            Gson gson = new Gson();
                            Temp temp = gson.fromJson(response.body().string(), Temp.class);
                            if(temp.getError_code() == 0){
                                context.showToast(temp.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
