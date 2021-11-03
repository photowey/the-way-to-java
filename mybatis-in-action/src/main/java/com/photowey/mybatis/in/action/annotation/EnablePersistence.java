package com.photowey.mybatis.in.action.annotation;

import com.photowey.mybatis.in.action.config.MybatisConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnablePersistence}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MybatisConfigure.class})
public @interface EnablePersistence {
}
