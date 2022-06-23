package com.photowey.mybatis.plus.mate.in.action.domain;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

import java.io.Serializable;

/**
 * {@code UserInfo}
 *
 * @author photowey
 * @date 2022/06/23
 * @since 1.0.0
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -3918194127369076857L;

    private Long id;
    private Long userId;
    @FieldEncrypt(algorithm = Algorithm.RSA)
    private String rsa;
}
