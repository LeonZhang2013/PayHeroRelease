package com.smartpos.payhero.txb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smartpos.payhero.R;
import com.smartpos.payhero.txb.bean.Order;
import com.smartpos.payhero.txb.bean.Temp;
import com.smartpos.payhero.txb.bean.TempData;
import com.smartpos.payhero.txb.net.ApiService;
import com.smartpos.payhero.txb.net.NetTools;
import com.smartpos.payhero.txb.net.ProcessObserver;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class ReceivableFragment extends BaseFragment {


    @BindView(R.id.amount_edit)
    EditText amountEdit;
    @BindView(R.id.amount_dis_edit)
    EditText amountDisEdit;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.message_text)
    TextView messageText;
    @BindView(R.id.amount_top)
    TextView amountTop;

    private TempData payUser;

    private String vip = "";
    private String noVip = "";


    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1,container,false);
    }

    @Override
    public void initView(View view) {
        messageText.setText("");
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                setAmountEdit();
            }
        };
        amountDisEdit.addTextChangedListener(tw);
        amountEdit.addTextChangedListener(tw);
    }

    @OnClick(R.id.phone_commit_btn)
    public void commitPhone(){
        String phone = phoneEdit.getText().toString();

        if(phone==null || phone.length()<11){
            showToast("号码有误");
            return;
        }

        JSONObject data = new JSONObject();
        try {
            data.put("cmd",2);
            data.put("uid",getConstantUser().getUid());
            data.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data.toString());
        ApiService service = NetTools.createService(ApiService.class);
        service.post(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProcessObserver<Response<ResponseBody>>(getActivity()) {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> response) {
                        try {
                            Gson gson = new Gson();
                            Temp temp = gson.fromJson(response.body().string(), Temp.class);
                            TempData tempData = gson.fromJson(temp.getData().toString(), TempData.class);
                            int status = tempData.getStatus();
                            if(status==1){
                                setData(tempData);
                            }else{
                                showDialog(temp.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


        //收起键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()) imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }


    AlertDialog alertDialog ;
    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("会员注册");
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("立即注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                String phone = phoneEdit.getText().toString();
                JSONObject data = new JSONObject();
                try {
                    data.put("cmd",7);
                    data.put("uid",getConstantUser().getUid());
                    data.put("phone",phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NetTools.post(data.toString(),new ProcessObserver<Response<ResponseBody>>(getActivity()) {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> response) {
                        try {
                            Gson gson = new Gson();
                            Temp temp = gson.fromJson(response.body().string(), Temp.class);
                            showToast(temp.getMsg());
                            TempData tempData = gson.fromJson(temp.getData().toString(), TempData.class);
                            setData(tempData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setAmountEdit();
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.show();
    }


    public void setData(TempData data){
        payUser = data;
        setAmountEdit();
    }

    public void setAmountEdit(){
        String amount = amountEdit.getText().toString();
        String amountDis = amountDisEdit.getText().toString();

        Order order = new Order();
        order.setUid(getConstantUser().getUid());
        order.setFzkprice(amount);
        order.setZkprice(amountDis);

        if(payUser!=null&&payUser.getStatus()==1){
            order.setDiscount(payUser.getDiscount()+"");
            messageText.setText("会员实付："+order.getFzkprice()+"+"+order.getZkpriceAfter()+"("+order.getDiscountX10()+"折) = "+order.getTprice());
        }else{
            messageText.setText("非会员实付：¥ "+order.getTprice());
        }
        amountTop.setText("消费金额:  ¥ "+order.getTprice());
        Constant.setOrder(order);
    }

    @OnClick(R.id.left_btn)
    public void leftBtn(){
        if(Constant.getOrder()!=null&&Constant.getOrder().getTprice()!=null){
            startActivity(com.smartpos.payhero.txb.PayMethdActivity.class);
        }else{
            showToast("请输入金额");
        }
    }

    @OnClick(R.id.right_btn)
    public void rightBtn(){
        if(Constant.getOrder()!=null&&Constant.getOrder().getTprice()!=null) {
            startActivity(com.smartpos.payhero.txb.PayCodeActivity.class);
        }else{
            showToast("请输入金额");
        }
    }

}
