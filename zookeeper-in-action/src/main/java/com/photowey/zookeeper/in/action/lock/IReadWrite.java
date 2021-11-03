package com.photowey.zookeeper.in.action.lock;

import java.util.Comparator;
import java.util.List;

/**
 * {@code IReadWrite}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public interface IReadWrite {
    String WRITE_SYMBOL = "w";
    String READ_SYMBOL = "r";
    String SPLIT = "-";

    default void sort(List<String> nodes) {
        System.out.println(nodes);
        nodes.sort(Comparator.comparing(o -> {
            if (o.contains(SPLIT)) {
                return o.split(SPLIT)[1];
            } else {
                return o;
            }
        }));
    }
}
