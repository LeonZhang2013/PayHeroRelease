package com.smartpos.payhero.txb;

import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;
import com.newland.smartpos.porting.impl.StringUtils;
import com.smartpos.payhero.R;
import com.smartpos.payhero.txb.bean.Temp;
import com.smartpos.payhero.txb.bean.User;
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

public class LoginActivity extends BaseActivity {

    @BindView(R.id.user_name)
    EditText editName;
    @BindView(R.id.pass_word)
    EditText editPass;


    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_login);

    }

    @OnClick(R.id.login_btn)
    public void login() {
        String username = editName.getText().toString();
        String password = editPass.getText().toString();

        if (StringUtils.isEmpty(username)) {
            showToast("用户名不能为空");
            return;
        }

        if (StringUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }


        JSONObject user = new JSONObject();
        try {
            user.put("cmd", 1);
            user.put("username", username);
            user.put("password", password);
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
                            User user = gson.fromJson(temp.getData().toString(), User.class);
                            setConstantUser(user);
                            startActivityFinish(MainActivity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
