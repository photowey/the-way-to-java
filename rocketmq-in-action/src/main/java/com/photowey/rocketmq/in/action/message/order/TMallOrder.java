package com.photowey.rocketmq.in.action.message.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code TMallOrder}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TMallOrder implements Serializable {

    private static final long serialVersionUID = -3203449787163809668L;

    private Long orderId;
    private String acton;
}
