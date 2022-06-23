package com.photowey.mybatis.plus.mate.in.action.domain;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

import java.io.Serializable;

/**
 * {@code User}
 *
 * @author photowey
 * @date 2022/06/23
 * @since 1.0.0
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 8857822751303333679L;

    private Long id;
    private String username;
    @FieldEncrypt(algorithm = Algorithm.PBEWithMD5AndDES)
    private String password;
    @FieldEncrypt
    private String email;
    @FieldEncrypt(algorithm = Algorithm.MD5_32)
    private String md5;
    @FieldEncrypt(algorithm = Algorithm.RSA)
    private String rsa;

}