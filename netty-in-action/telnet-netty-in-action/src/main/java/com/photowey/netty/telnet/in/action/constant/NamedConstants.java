package com.photowey.netty.telnet.in.action.constant;

/**
 * {@code TelnetConstants}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public interface NamedConstants {

    String TELNET_STRING_END = new String(new byte[]{(byte) 13, (byte) 10});
    String EMPTY_STRING = "";

    String SPACE_SEPARATOR = "\\s+";

    String TELNET_SERVER_ENABLE = "named.telnet.server.enable";
    String TELNET_SERVER_SECURITY_ENABLE = "named.telnet.security.enabled";

    String TELNET_SERVER_WORKER_THREAD_POOL_NAME = "worker";

    String TELNET_COMMAND_THREAD_POOL_NAME = "cmd";

    String TELNET_SESSION_PROMPT = "named-telnet>";

    int DEFAULT_TELNET_PORT = 7923;
    int DEFAULT_SELECT_PORT_SIZE = 256;


}
