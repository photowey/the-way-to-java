package com.photowey.zookeeper.in.action.lock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@code OrderNumFactory}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public class OrderNumFactory {

    private static int i = 0;

    public String createOrderNum() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
        return simpleDateFormat.format(new Date()) + ++i;
    }
}