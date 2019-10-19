package com.niuxiaofei.service;

import com.github.pagehelper.PageInfo;
import com.niuxiaofei.entity.Area;
import com.niuxiaofei.entity.Classes;
import com.niuxiaofei.entity.Student;

import java.util.List;

public interface StudentService {
    /**
     * 列表页
     *
     * @param student
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<Student> getPageInfo(Student student, Integer pageNo, Integer pageSize);

    /**
     * 保存（新增、修改）
     *
     * @param student
     * @return
     */
    boolean save(Student student);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    boolean deleteByIds(String ids);

    /**
     * 按Id，查询student
     *
     * @param id
     * @return
     */
    Object getById(Integer id);
    /**
     * 查询班级列表
     * @return
     */
    List<Classes> getClassesList();

    /**
     * 根据pid，查询城市列表
     * @param pid
     * @return
     */
    List<Area> getAraeListByPid(Integer pid);
}
