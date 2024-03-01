package com.zjh.backend.mapper;

import com.zjh.backend.pojo.entity.Charts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
* @author jiahaozhang
* @description 针对表【charts(用户需求信息表)】的数据库操作Mapper
* @createDate 2024-03-01 22:06:30
* @Entity generator.domain.Charts
*/
@Mapper
public interface ChartsMapper extends BaseMapper<Charts> {

    @Delete("delete from charts where id = #{id}")
    public int deleteById(Long id);


}




