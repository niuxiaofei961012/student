package com.niuxiaofei.dao;

import com.niuxiaofei.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface AreaDao {
    @Select("select id,name,pid from t_area")
    List<Area> selectAll();

    @Select("select * from t_area where pid=#{pid}")
    List<Area> selectListByPid(@Param("pid") Integer pid);

    @Select("select * from t_area where id=#{id}")
    Area selectById(@Param("id") Integer id);

    List<Area> selectListByIds(@Param("areaIdList") List<Integer> areaIdList);
}
