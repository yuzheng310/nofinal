package com.example.nofinal.dohttp;


import android.content.Context;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by liu kun on 2017/10/23 0023.
 */

public class MySubscriber<T> implements SubscriberHandlerListener, Observer<T> {
    private forhttpLisener listener;
    private Context context;
    private SubscriberHandler handler;

    public MySubscriber(forhttpLisener listener,
                        Context context) {
        this.listener = listener;
        this.context = context;
        handler = new SubscriberHandler(this, context, true);
    }


    @Override
    public void onError(Throwable e) {
        dismissPd();
    }

    @Override
    public void onComplete() {
        dismissPd();
    }


    @Override
    public void onSubscribe(Disposable d) {
        showPd();;
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }


    private void showPd() {
        handler.obtainMessage(SubscriberHandler.SEND_MESSAGE).sendToTarget();
    }

    private void dismissPd() {
        handler.obtainMessage(SubscriberHandler.CANCEL_MESSAGE).sendToTarget();
    }

    @Override
    public void cancelRequest() {

    }
}
