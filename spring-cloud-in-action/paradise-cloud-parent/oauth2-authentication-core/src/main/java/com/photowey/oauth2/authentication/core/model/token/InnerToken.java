package com.photowey.oauth2.authentication.core.model.token;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * {@code InnerToken}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Data
public class InnerToken implements Serializable {

    private String pp;
    private String ui;
    private String jti;
    private Integer ei;
    private List<String> au;
}