package com.niuxiaofei.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.niuxiaofei.dao.AreaDao;
import com.niuxiaofei.dao.ClassesDao;
import com.niuxiaofei.dao.StudentDao;
import com.niuxiaofei.entity.Area;
import com.niuxiaofei.entity.Classes;
import com.niuxiaofei.entity.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    StudentDao studentDao;
    @Resource
    ClassesDao classesDao;
    @Resource
    AreaDao areaDao;

    @Override
    public PageInfo<Student> getPageInfo(Student student, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        //查询所有学生数据
        List<Student> students = studentDao.selectList(student);
        //查询所有班级信息
        List<Classes> classes = classesDao.selectList();
        //处理学生的所有地区id
        List<Integer> areaIdList = students.stream().map(s -> s.getProvice_id()).collect(Collectors.toList());
        areaIdList.addAll(students.stream().map(s -> s.getCity_id()).collect(Collectors.toList()));
        areaIdList.addAll(students.stream().map(s -> s.getArea_id()).collect(Collectors.toList()));
        List<Area> areas = areaDao.selectListByIds(areaIdList);
        students.forEach(s->{
            classes.forEach(c->{
                if(s.getClasses_id()==c.getId()){
                    s.setClasses_name(c.getName());
                }
            });
            areas.forEach(area->{
                if(s.getProvice_id().intValue()==area.getId().intValue()){
                    s.setProvice_name(area.getName());
                }else if(s.getCity_id().intValue()==area.getId().intValue()){
                    s.setCity_name(area.getName());
                }else if(s.getArea_id().intValue()==area.getId().intValue()){
                    s.setArea_name(area.getName());
                }
            });
        });
        System.out.println(students);
        return new PageInfo<>(students);
    }

    @Override
    public boolean save(Student student) {
        if (student.getId() == null) {
            return studentDao.insert(student) > 0;
        }
        return studentDao.update(student) > 0;
    }

    @Override
    public boolean deleteByIds(String ids) {
        return studentDao.deleteByIds(ids) > 0;
    }

    @Override
    public Object getById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        //学生信息
        Student students = studentDao.selectById(id);
        //市信息
        List<Area> cityList = areaDao.selectListByPid(students.getProvice_id());
        //区信息
        List<Area> areaList = areaDao.selectListByPid(students.getCity_id());

        map.put("students", students);
        map.put("cityList", cityList);
        map.put("areaList", areaList);

        return map;
    }

    @Override
    public List<Classes> getClassesList() {
        return classesDao.selectList();
    }

    @Override
    public List<Area> getAraeListByPid(Integer pid) {
        return areaDao.selectListByPid(pid);
    }
}
