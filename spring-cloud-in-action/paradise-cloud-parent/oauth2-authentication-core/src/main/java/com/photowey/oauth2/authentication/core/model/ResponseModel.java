package com.photowey.oauth2.authentication.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code ResponseModel}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel implements Serializable {

    private static final long serialVersionUID = -2182374801594872295L;

    private int status;
    private String code;
    private String message;
    private Object data;
}