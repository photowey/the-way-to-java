package com.photowey.rxtx.in.action.serialport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * {@code SerialPortHandlerTest}
 *
 * @author photowey
 * @date 2022/02/10
 * @since 1.0.0
 */
class SerialPortHandlerTest {

    /**
     * Stable Library
     * =========================================
     * Native lib Version = RXTX-2.2-20081207 Cloudhopper Build rxtx.cloudhopper.net
     * Java lib Version   = RXTX-2.1-7
     * WARNING:  RXTX Version mismatch
     * Jar version = RXTX-2.1-7
     * native lib Version = RXTX-2.2-20081207 Cloudhopper Build rxtx.cloudhopper.net
     */
    @Test
    void testSerialPost() {

        List<String> ports = SerialPortHandler.findPorts();
        Assertions.assertEquals(2, ports.size());
        Assertions.assertTrue(ports.contains("COM1"));
        Assertions.assertTrue(ports.contains("COM2"));

    }

}