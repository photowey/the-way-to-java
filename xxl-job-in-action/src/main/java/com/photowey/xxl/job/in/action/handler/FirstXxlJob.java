package com.photowey.xxl.job.in.action.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * {@code FirstXxlJob}
 *
 * @author photowey
 * @date 2022/04/10
 * @since 1.0.0
 */
@Slf4j
@Component
public class FirstXxlJob {

    @XxlJob("helloJobHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
        log.info("-------------------------------------------- I'm alive.");
    }

}