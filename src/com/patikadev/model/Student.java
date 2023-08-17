package com.patikadev.model;

import com.patikadev.helper.MyHelper;

public class Student extends User{
    public Student(int id, String fullname, String username, String password) {
        super(id, fullname, username, password, MyHelper.USER_TYPE_NAME_STUDENT);
    }
}
