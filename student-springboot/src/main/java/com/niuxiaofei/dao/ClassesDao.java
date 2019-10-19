package com.niuxiaofei.dao;

import com.niuxiaofei.entity.Classes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassesDao {
    @Select("select * from t_classes")
    List<Classes> selectList();

    @Select("select * from t_classes where id=#{id}")
    Classes selectById(@Param("id") Integer id);
}
