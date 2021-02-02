package com.wangsheng.task;

public interface Task<T> {
    void onStart(T object);

    void onEnd(T object);

    void onError(T object,Exception e);
}
