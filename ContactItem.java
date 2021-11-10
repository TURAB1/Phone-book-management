package com.example.phonebookmanagement;

public class ContactItem {
    String name;
    String mobile;
    int age;


    public ContactItem(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public ContactItem(String name, String mobile, int age) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
