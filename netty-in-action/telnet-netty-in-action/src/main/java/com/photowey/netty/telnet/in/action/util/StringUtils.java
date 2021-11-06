package com.photowey.netty.telnet.in.action.util;

import com.photowey.netty.telnet.in.action.constant.NamedConstants;

/**
 * {@code StringUtils}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public final class StringUtils {

    private StringUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static boolean isEmpty(String target) {
        return null == target || target.trim().length() == 0;
    }

    public static boolean isNotEmpty(String target) {
        return !isEmpty(target);
    }

    public static String stripPrefixIfNecessary(String target) {
        if (StringUtils.isEmpty(target)) {
            return NamedConstants.EMPTY_STRING;
        }

        return target.replaceAll("^-*", "");
    }
}