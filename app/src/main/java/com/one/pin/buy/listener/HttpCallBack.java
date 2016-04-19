package com.one.pin.buy.listener;

/**
 * Created by liuguilin on 16/4/19.
 */
public interface HttpCallBack<T,K,V> {

    void successCall(T t);
    void failedCall(K k);
    void error(V v);
    void complete();

}
