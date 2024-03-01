package com.zjh.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjh.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author jiahaozhang
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-01-09 22:31:28
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where userAccount like #{userAccount}")
    User selectByUserAccount(@Param("userAccount") String userAccount);

}




