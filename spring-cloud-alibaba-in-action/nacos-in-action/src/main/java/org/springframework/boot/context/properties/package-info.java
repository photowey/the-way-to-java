/**
 * {@code org.springframework.boot.context.properties}
 * 解决异常报错
 * Error creating bean with name 'nacosConfigurationPropertiesBinder':
 * Bean instantiation via constructor failed; nested exception is org.springframework.beans.BeanInstantiationException:
 * Failed to instantiate [com.alibaba.boot.nacos.config.binder.NacosBootConfigurationPropertiesBinder]:
 * Constructor threw exception; nested exception is java.lang.NoClassDefFoundError: org/springframework/boot/context/properties/ConfigurationBeanFactoryMetadata
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
package org.springframework.boot.context.properties;