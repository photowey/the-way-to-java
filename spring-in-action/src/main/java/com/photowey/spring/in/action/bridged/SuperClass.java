package com.photowey.spring.in.action.bridged;

/**
 * {@code SuperClass}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
public interface SuperClass<T> {

    String sayHello(T who);
}
