package com.spring.ioc.demo.factory;

import com.spring.ioc.demo.bean.School;
import com.spring.ioc.demo.bean.Student;
import org.springframework.beans.factory.FactoryBean;

public class PojoFactoryBean implements FactoryBean {
    private String type;

    @Override
    public Object getObject() throws Exception {
        if ("student".equals(type)) {
            Student student = new Student();
            student.setAge(29);
            student.setName("he yong & eric.he");
            return student;
        } else {
            School school = new School();
            school.setAddress("CHINA SHEN ZHEN");
            school.setSchoolName("SZPT");
            school.setStudentNumber(1001);
            return school;
        }

    }

    @Override
    public Class getObjectType() {
        return School.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}