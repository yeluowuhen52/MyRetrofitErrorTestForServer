package com.marsjiang.myretrofiterrorhandle.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseObserver
 * Created by jaycee on 2017/6/23.
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntity<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            Gson gson = new Gson();
            Log.d("returnInfo", "onNext" + gson.toJson(value));
            onHandleSuccess(t);
        } else {
            Log.d("returnInfo", "onNext走了失败！" );
            onHandleError(value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError:" + e.toString());
        onHandleError(e.toString());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
