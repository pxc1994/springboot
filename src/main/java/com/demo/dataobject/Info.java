package com.demo.dataobject;

import lombok.Data;


@Data
public class Info {

    public Info() {
        test = new Test();
    }

    private int id;

    private String name;

    private Test test;


    public static void main(String[] args) {
        Info info = new Info();
        info.getTest().setAge(20);
        System.out.println(info.toString());
    }


}
