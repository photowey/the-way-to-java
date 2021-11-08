package com.photowey.mybatis.in.action.mybatis.dynamic.enums;

/**
 * {@code IdTypeEnum}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public enum IdTypeEnum {
    /**
     * 数据子自增长
     */
    AUTO("AUTO", 0),
    /**
     * 用户自己输入
     */
    INPUT("用户输入ID", 1),

    /**
     * 框架默认生成-整型类型
     */
    ID_WORKER("ID_WORKER", 2),
    /**
     * 框架默认生成-字符串类型
     */
    ID_WORKER_STRING("STRING_ID_WORKER", 3),
    /**
     * 框架默认生成: UUID
     */
    UUID("UUID", 4),
    /**
     * 数据表未设置主键类型
     */
    NONE("NONE", 5);

    private final String name;
    private final int value;

    IdTypeEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String alisa() {
        return name;
    }

    public int value() {
        return value;
    }
}
