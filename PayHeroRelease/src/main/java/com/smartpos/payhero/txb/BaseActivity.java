package com.smartpos.payhero.txb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.smartpos.payhero.txb.bean.User;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    private static Constant CONSTANT;
    Context context = this;

    //设置全局常量
    public void  setConstantUser(User user){
        CONSTANT.setUser(user);
    }

    //设置全局常量
    public User getConstantUser(){
        return CONSTANT.getUser();
    }

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    public void startActivity(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }
    public void startActivityFinish(Class activity){
        startActivity(activity);
        finish();
    }
}
