package com.photowey.mybatis.plus.mate.in.action.domain.dto;

import com.photowey.mybatis.plus.mate.in.action.domain.User;
import com.photowey.mybatis.plus.mate.in.action.domain.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * {@code UserDto}
 *
 * @author photowey
 * @date 2022/06/23
 * @since 1.0.0
 */
@Data
public class UserDto extends User {

    private List<UserInfo> userInfos;
}
