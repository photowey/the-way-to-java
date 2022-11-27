package com.photowey.spring.boot.v3.aot.service.impl;

import com.photowey.spring.boot.v3.aot.repository.CustomerRepository;
import com.photowey.spring.boot.v3.aot.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * {@code CustomerServiceImpl}
 *
 * @author photowey
 * @date 2022/11/27
 * @since 1.0.0
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
