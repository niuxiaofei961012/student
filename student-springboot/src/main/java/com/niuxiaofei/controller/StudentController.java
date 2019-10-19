package com.niuxiaofei.controller;

import com.github.pagehelper.PageInfo;
import com.niuxiaofei.entity.Area;
import com.niuxiaofei.entity.Classes;
import com.niuxiaofei.entity.Student;
import com.niuxiaofei.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Value("${file.domain}")
    private String fileDomain;
    @Value("${file.path}")
    private String filePath;
    @Resource
    private StudentService studentService;

    /**
     * 列表页
     *
     * @param student
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("getPageInfo")
    public PageInfo<Student> getPageInfo(Student student,
                                         @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        return studentService.getPageInfo(student, pageNo, pageSize);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("deleteByIds")
    public boolean deleteByIds(String ids) {
        return studentService.deleteByIds(ids);
    }

    /**
     * 保存/修改
     *
     * @param student
     * @return
     */
    @PostMapping("save")
    public boolean deleteByIds(@RequestBody Student student) {
        return studentService.save(student);
    }

    /**
     * 根据Id查询student
     *
     * @param id
     * @return
     */
    @GetMapping("getById")
    public Object getById(Integer id) {
        return studentService.getById(id);
    }

    @GetMapping("initData")
    public Object getInitData(){
        Map<String,Object> map = new HashMap<>();
        //班级列表
        List<Classes> classesList = studentService.getClassesList();
        //省列表
        List<Area> proviceList = studentService.getAraeListByPid(1);
        map.put("classesList",classesList);
        map.put("proviceList",proviceList);
        return map;
    }

    @GetMapping("getAreas")
    public Object getAreas(Integer pid){
        List<Area> araeListByPid = studentService.getAraeListByPid(pid);
        return araeListByPid;
    }

    @PostMapping("upLoad")
    public Object upLoad(@RequestParam("file") MultipartFile file){
        Map<String,Object> result = new HashMap<>();
        String fileName = UUID.randomUUID().toString()+".jpg";
        File saveFile = new File(filePath+fileName);
        String imageUrl = fileDomain+fileName;
        result.put("imageUrl",imageUrl);
        result.put("fileName",fileName);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
