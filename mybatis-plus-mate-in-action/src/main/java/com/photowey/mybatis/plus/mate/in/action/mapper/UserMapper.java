package com.photowey.mybatis.plus.mate.in.action.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photowey.mybatis.plus.mate.in.action.domain.User;
import com.photowey.mybatis.plus.mate.in.action.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * {@code UserMapper}
 *
 * @author photowey
 * @date 2022/06/23
 * @since 1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set password=#{u.password},email=#{u.email} where id=#{id}")
    Integer testUpdateById(@Param("id") Long id, @Param("u") User user);

    Integer insertBatchTest(@Param("userList") List<User> userList);

    Integer updateBatchUserById(@Param("userList") List<User> userList);

    List<UserDto> selectUserDtoList();

    UserDto selectUserDto(@Param("id") Long id);
}