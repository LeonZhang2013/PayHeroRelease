package com.smartpos.payhero.txb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smartpos.payhero.txb.bean.User;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yy520 on 2018-4-2.
 */

public abstract class BaseFragment extends Fragment {
    private Constant constant;
    private Unbinder unbinder;

    public void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }


    public void startActivity(Class activity){
        Intent intent = new Intent(getActivity(),activity);
        startActivity(intent);
    }

    public void startActivity(Class activity,String data){
        Intent intent = new Intent(getActivity(),activity);
        intent.putExtra("data",data);
        startActivity(intent);
    }

    public abstract View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    public abstract void initView(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(inflater,container,savedInstanceState);
        bindButterKnifeView(view);
        initView(view);
        return view;
    }

    //设置全局常量
    public User getConstantUser(){
       return constant.getUser();
    }

    public void bindButterKnifeView(View view){
        unbinder = ButterKnife.bind(this, view);
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
