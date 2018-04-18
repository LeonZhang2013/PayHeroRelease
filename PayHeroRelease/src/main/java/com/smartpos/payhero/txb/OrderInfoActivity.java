package com.smartpos.payhero.txb;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smartpos.payhero.R;
import com.smartpos.payhero.txb.bean.TempData;
import com.smartpos.payhero.txb.bean.TempDataList;
import com.smartpos.payhero.txb.net.ApiService;
import com.smartpos.payhero.txb.net.NetTools;
import com.smartpos.payhero.txb.net.ProcessObserver;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OrderInfoActivity extends BaseActivity {


    @BindView(R.id.item_0)
    TextView item_0;
    @BindView(R.id.item_1)
    TextView item_1;
    @BindView(R.id.item_2)
    TextView item_2;
    @BindView(R.id.item_3)
    TextView item_3;
    @BindView(R.id.item_4)
    TextView item_4;
    @BindView(R.id.item_5)
    TextView item_5;

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.order_info_layout);

        String order_id = getIntent().getStringExtra("data");

        JSONObject user = new JSONObject();
        try {
            user.put("cmd", 5);
            user.put("order_id", order_id);
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
                            TempDataList temp = gson.fromJson(response.body().string(), TempDataList.class);
                            if(temp.getError_code()==0){
                                showToast(temp.getMsg());
                            }
                            inflateData(temp.getData().get(0));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void inflateData(TempData tempData) {

        item_0.setText("支付时间："+tempData.getTime());
        item_1.setText("折扣金额："+tempData.getTprice());
        item_2.setText("非折扣金额："+tempData.getDiscount());
        item_3.setText("总金额："+tempData.getTprice());
        item_4.setText("支付手机："+(tempData.getPhone()==null?"":tempData.getPhone()));
        item_5.setText("支付方式："+getTypeStr(tempData.getPtype()));
    }

    private String getTypeStr(String ptype) {
        String type = "其他";
        switch (Integer.parseInt(ptype)){
            case 0:
                type = "现金";
                break;
            case 1:
                type = "刷卡";
                break;
            case 2:
                type = "支付宝";
                break;
            case 3:
                type = "微信";
                break;
        }
        return type;
    }


}
