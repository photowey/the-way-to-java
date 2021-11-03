package com.photowey.mybatis.in.action.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.mybatis.in.action.domain.entity.Employee;
import com.photowey.mybatis.in.action.repository.EmployeeRepository;
import com.photowey.mybatis.in.action.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * {@code EmployeeServiceImpl}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeRepository, Employee> implements EmployeeService {

}

