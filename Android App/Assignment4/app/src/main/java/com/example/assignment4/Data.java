package com.example.assignment4;

import java.io.Serializable;

public class Data implements Serializable {
    private int app_id;
    private String app_uname,name,rollno,program,dept,email,subject,body,date;
    private String co_approv,ad_approv,co_comment,ad_comment,app_status;

    public String getCo_approv() {
        return co_approv;
    }

    public String getApp_status() {
        return app_status;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    public void setCo_approv(String co_approv) {
        this.co_approv = co_approv;
    }

    public String getAd_approv() {
        return ad_approv;
    }

    public void setAd_approv(String ad_approv) {
        this.ad_approv = ad_approv;
    }

    public String getCo_comment() {
        return co_comment;
    }

    public void setCo_comment(String co_comment) {
        this.co_comment = co_comment;
    }

    public String getAd_comment() {
        return ad_comment;
    }

    public String getApp_uname() {
        return app_uname;
    }

    public void setApp_uname(String app_uname) {
        this.app_uname = app_uname;
    }

    public void setAd_comment(String ad_comment) {
        this.ad_comment = ad_comment;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int id) {
        this.app_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
