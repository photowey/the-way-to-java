package com.photowey.oauth2.authentication.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * {@code AuthUser}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends JwtModel {

    private static final long serialVersionUID = -1895143987787345736L;

    private String userId;
    private String userName;
    private List<String> authorities;
}
