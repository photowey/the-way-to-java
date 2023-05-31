package com.photowey.mybatis.in.action.plugin.shared;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * {@code MybatisLoader}
 *
 * @author photowey
 * @date 2023/05/31
 * @since 1.0.0
 */
@Component
public class MybatisLoader {

    @Resource
    private MybatisScanner scanner;

    public void doLoad() {
        scanner.scanner();
    }

}