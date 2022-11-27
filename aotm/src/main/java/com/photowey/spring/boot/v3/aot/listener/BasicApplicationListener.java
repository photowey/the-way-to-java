package com.photowey.spring.boot.v3.aot.listener;

import com.photowey.spring.boot.v3.aot.core.domain.entity.Customer;
import com.photowey.spring.boot.v3.aot.repository.CustomerRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * {@code BasicApplicationListener}
 *
 * @author photowey
 * @date 2022/11/27
 * @since 1.0.0
 */
@Component
public class BasicApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final CustomerRepository customerRepository;

    public BasicApplicationListener(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.customerRepository.saveAll(
                Stream.of("A", "B", "C")
                        .map(name -> new Customer(null, name))
                        .toList()
        ).forEach(System.out::println);

    }
}
