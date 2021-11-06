package com.photowey.netty.telnet.in.action.netty.port;

import com.photowey.netty.telnet.in.action.constant.NamedConstants;
import com.photowey.netty.telnet.in.action.exception.NamedException;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * {@code PortSelector}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public final class PortSelector {

    public static final int MIN_PORT_NUMBER = NamedConstants.DEFAULT_TELNET_PORT;

    public static final int MAX_PORT_NUMBER = NamedConstants.DEFAULT_TELNET_PORT + NamedConstants.DEFAULT_SELECT_PORT_SIZE;

    private PortSelector() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public synchronized static int selectAvailablePort(int defaultPort, int searchThreshold) {
        for (int i = defaultPort, floor = defaultPort + searchThreshold; i <= floor; i++) {
            try {
                if (portAvailable(i)) {
                    return i;
                }
            } catch (IllegalArgumentException e) {
                // ignore
            }
        }

        return -1;
    }


    private static boolean portAvailable(int port) {
        checkPort(port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            // do nothing
        }

        return false;
    }

    private static void checkPort(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new NamedException(String.format("the port: %d invalid, check please!", port));
        }
    }
}
