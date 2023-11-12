package io.github.photowey.proguard.in.action.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code Hello}
 *
 * @author photowey
 * @date 2023/11/12
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hello implements Serializable {

    private static final long serialVersionUID = -8459786975708237408L;

    private String variableString;
    private Integer variableInt;
    private Long variableLong;
    private Object variableObject;
}
