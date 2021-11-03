package com.photowey.redis.in.action.queue.impl.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * {@code StreamQueueMessage}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StreamQueueMessage implements Serializable {

    private static final long serialVersionUID = -203659320287106122L;

    private String name;
    private Integer age;
    private String content;
    private String group;
}