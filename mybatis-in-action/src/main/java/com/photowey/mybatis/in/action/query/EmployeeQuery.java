package com.photowey.mybatis.in.action.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code EmployeeQuery}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeQuery implements Serializable {

  private static final long serialVersionUID = -437599164418675624L;

  private Long orgId;
  private String orgName;
  private String employeeNo;
}
