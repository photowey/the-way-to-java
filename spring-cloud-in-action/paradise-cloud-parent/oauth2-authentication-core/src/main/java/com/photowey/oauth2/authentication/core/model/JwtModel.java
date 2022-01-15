package com.photowey.oauth2.authentication.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * {@code JwtModel}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Data
public class JwtModel implements Serializable {

    private static final long serialVersionUID = 2207193447086933002L;

    protected String jti;
    protected Long expiredIn;

}
