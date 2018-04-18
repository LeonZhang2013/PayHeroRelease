package com.smartpos.payhero.txb.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by yy520 on 2018-4-9.
 */

public abstract class ProcessObserver<T>  implements Observer<T> {

    private Context context;
    public ProcessObserver(Context context) {
        this.context = context;
    }

    ProgressDialog mProgressDialog;
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("加载中...");
        mProgressDialog.show();
    }

    @Override
    abstract public void onNext(@NonNull T t);

    @Override
    public void onError(@NonNull Throwable e) {
        Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
        mProgressDialog.dismiss();
    }

    @Override
    public void onComplete() {
        mProgressDialog.dismiss();
    }
}
