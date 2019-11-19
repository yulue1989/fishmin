package com.xuecheng.freemarker.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class Student {
    private String name;
    private int age;
    private Date birthday;
    private Float mondy; //钱包
    private List<Student> friends; //好友列表
    private Student bestFriend;
}
