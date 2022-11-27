package com.photowey.spring.boot.v3.aot.core.domain.entity;

import org.springframework.data.annotation.Id;

/**
 * {@code Customer}
 *
 * @author photowey
 * @date 2022/11/27
 * @since 1.0.0
 */
public record Customer(@Id Integer id, String name) {

}