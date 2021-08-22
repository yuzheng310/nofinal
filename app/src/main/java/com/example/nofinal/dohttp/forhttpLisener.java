package com.example.nofinal.dohttp;

public interface forhttpLisener<T> {
    /** 重写onNext用于回调 */
    void onNext(T t);
}
