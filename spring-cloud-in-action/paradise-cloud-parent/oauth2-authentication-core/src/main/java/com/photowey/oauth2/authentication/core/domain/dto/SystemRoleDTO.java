package com.photowey.oauth2.authentication.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code SystemRoleDTO}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemRoleDTO implements Serializable {

    private static final long serialVersionUID = -6199980455229669383L;

    private Long id;
    private String name;
    private String code;
    private Integer status;
}
