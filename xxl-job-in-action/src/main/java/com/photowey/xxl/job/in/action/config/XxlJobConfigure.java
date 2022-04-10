package com.photowey.xxl.job.in.action.config;

import com.photowey.xxl.job.in.action.property.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code XxlJobConfigure}
 *
 * @author photowey
 * @date 2022/04/10
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobConfigure {

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties props) {
        log.info("--- >>> xxl-job config init... <<< ---");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(props.getAdmin().getAddresses());
        xxlJobSpringExecutor.setAppname(props.getExecutor().getAppName());
        xxlJobSpringExecutor.setAddress(props.getExecutor().getAddress());
        xxlJobSpringExecutor.setIp(props.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(props.getExecutor().getPort());
        xxlJobSpringExecutor.setAccessToken(props.getAccessToken());
        xxlJobSpringExecutor.setLogPath(props.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(props.getExecutor().getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

}
