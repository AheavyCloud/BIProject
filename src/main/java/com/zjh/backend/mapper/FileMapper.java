package com.zjh.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjh.backend.pojo.entity.Charts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface FileMapper extends BaseMapper<Charts> {
    // 根据文件字段，创建文件数据表，文件数据表 = charts_(图片id)
    // 对数据表中的数据进行插入
    void createNewTable(@Param("tableName") String tableName,
                        @Param("fieldName1") String fieldName1,
                        @Param("fieldName2") String fieldName2);
    // 对数据插入 + 查找当前id
    Long startTransaction(Charts charts);
}
