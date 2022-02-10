/*
 * Copyright © 2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.rxtx.in.action.serialport;

import gnu.io.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@code SerialPortHandler}
 *
 * @author photowey
 * @date 2022/02/09
 * @since 1.0.0
 */
public final class SerialPortHandler {

    private SerialPortHandler() {
    }

    /**
     * 查找所有可用实例
     *
     * @return 可用端口名称列表
     */
    public static Set<CommPortIdentifier> findAvailableSerialPorts() {
        Set<CommPortIdentifier> ports = new HashSet<>();
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier com = portList.nextElement();
            if (com.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                try {
                    CommPort thePort = com.open(com.getName(), 50);
                    thePort.close();
                    ports.add(com);
                } catch (PortInUseException ignored) {
                }
            }
        }

        return ports;
    }

    /**
     * 查找所有可用端口
     *
     * @return 可用端口名称列表
     */
    public static List<String> findPorts() {
        Set<CommPortIdentifier> portSet = findAvailableSerialPorts();
        return portSet.stream().map(CommPortIdentifier::getName).collect(Collectors.toList());
    }

    /**
     * 打开串口
     * 默认波特率: 115200
     *
     * @param port 端口名称
     * @return 串口对象
     */
    public static SerialPort openPort(String port) {
        return openPort(port, 115200);
    }

    /**
     * 打开串口
     * 默认: 3_000 ms 超时
     *
     * @param port     端口名称
     * @param baudRate 波特率
     * @return 串口对象
     */
    public static SerialPort openPort(String port, int baudRate) {
        return openPort(port, baudRate, 3_000);
    }

    /**
     * 打开串口
     * <li>
     * DATABITS_5 数据位为5
     * <p>
     * DATABITS_6 数据位为6
     * <p>
     * DATABITS_7 数据位为7
     * <p>
     * DATABITS_8 数据位为8
     * <p>
     * PARITY_NONE 空格检验
     * <p>
     * PARITY_ODD 奇检验
     * <p>
     * PARITY_EVEN 偶检验
     * <p>
     * PARITY_MARK 标记检验
     * <p>
     * PARITY_SPACE 无检验
     * <p>
     * STOPBITS_1 停止位为1
     * <p>
     * STOPBITS_2 停止位为2
     * <p>
     * STOPBITS_1_5 停止位为1.5
     * </li>
     *
     * @param port     端口名称
     * @param baudRate 波特率
     * @param timeout  超时时间
     * @return 串口对象
     */
    public static SerialPort openPort(String port, int baudRate, int timeout) {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
            CommPort commPort = portIdentifier.open(port, timeout);
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                return serialPort;
            } else {
                throw new RuntimeException("not a serial port");
            }
        } catch (UnsupportedCommOperationException e1) {
            throw new RuntimeException("unsupported comm operation exception");
        } catch (NoSuchPortException e3) {
            throw new RuntimeException("no such port exception");
        } catch (PortInUseException e) {
            throw new RuntimeException("the serial port busy");
        }
    }

    /**
     * 关闭串口
     *
     * @param serialPort 待关闭的串口对象
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
        }
    }

    /**
     * 往串口发送数据
     *
     * @param serialPort 串口对象
     * @param data       待发送数据 (注意进制问题)
     */
    public static void write(SerialPort serialPort, byte[] data) {
        try (OutputStream out = serialPort.getOutputStream()) {
            out.write(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("send data to serial port failure");
        }
    }

    /**
     * 从串口读取数据
     *
     * @param serialPort 当前已建立连接的 {@link SerialPort} 对象
     * @return 读取到的数据
     */
    public static String read(SerialPort serialPort) {
        try (InputStream in = serialPort.getInputStream()) {
            return new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            throw new RuntimeException("read data from serial port failure");
        }
    }

    /**
     * 添加监听器
     *
     * @param port     串口对象
     * @param listener 串口监听器
     */
    public static void addListener(SerialPort port, SerialPortEventListener listener) {
        try {
            port.addEventListener(listener);
            port.notifyOnDataAvailable(true);
            port.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            throw new RuntimeException("too many listeners", e);
        }
    }
}
