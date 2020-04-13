package com.spring.ioc.demo.bean;

import java.io.Serializable;

public class School implements Serializable {
    private static final long serialVersionUID = -5895417384631873266L;
    private String schoolName;
    private String address;
    private int studentNumber;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "School [schoolName=" + schoolName + ", address=" + address
                + ", studentNumber=" + studentNumber + "]";
    }
}
