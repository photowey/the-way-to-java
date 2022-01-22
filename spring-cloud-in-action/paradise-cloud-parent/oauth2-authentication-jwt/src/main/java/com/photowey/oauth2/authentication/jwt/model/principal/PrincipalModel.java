package com.photowey.oauth2.authentication.jwt.model.principal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code PrincipalModel}
 *
 * @author photowey
 * @date 2022/01/22
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalModel implements Serializable {

    private static final long serialVersionUID = 3996060402527577481L;

    private Long id;
    private String userId;
    private String userName;
}
