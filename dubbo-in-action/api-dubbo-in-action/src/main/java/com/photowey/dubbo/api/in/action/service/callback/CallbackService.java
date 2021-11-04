package com.photowey.dubbo.api.in.action.service.callback;

/**
 * {@code CallbackService}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public interface CallbackService {

    String sayHello(String name);

    void sayHelloCallback(String name, CallbackListener callback);
}
