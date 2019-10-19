package com.niuxiaofei.dao;

import com.niuxiaofei.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentDao {
    List<Student> selectList(Student student);

    @Delete("delete from t_student where id in (${value})")
    int deleteByIds(String ids);

    int update(Student student);

    int insert(Student student);

    @Select("select * from t_student where id=#{value}")
    Student selectById(Integer id);
}
