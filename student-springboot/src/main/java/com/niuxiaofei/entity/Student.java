package com.niuxiaofei.entity;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {

    private static final long serialVersionUID = -4912798790414165891L;

    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private Integer ageStart;
    private Integer ageEnd;
    private Date birthday;
    private String header_img;
    private Integer classes_id;
    private String classes_name;
    private Integer provice_id;
    private Integer city_id;
    private Integer area_id;
    private String provice_name;
    private String city_name;
    private String area_name;
    private String introduction;
    private Date create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public String getProvice_name() {
        return provice_name;
    }

    public void setProvice_name(String provice_name) {
        this.provice_name = provice_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClasses_name() {
        return classes_name;
    }

    public void setClasses_name(String classes_name) {
        this.classes_name = classes_name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHeader_img() {
        return header_img;
    }

    public void setHeader_img(String header_img) {
        this.header_img = header_img;
    }

    public Integer getClasses_id() {
        return classes_id;
    }

    public void setClasses_id(Integer classes_id) {
        this.classes_id = classes_id;
    }

    public Integer getProvice_id() {
        return provice_id;
    }

    public void setProvice_id(Integer provice_id) {
        this.provice_id = provice_id;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Integer ageStart) {
        this.ageStart = ageStart;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", birthday=" + birthday +
                ", header_img='" + header_img + '\'' +
                ", classes_id=" + classes_id +
                ", provice_id=" + provice_id +
                ", city_id=" + city_id +
                ", area_id=" + area_id +
                ", introduction='" + introduction + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
