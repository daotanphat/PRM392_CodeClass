package com.dtp.myapplication.bean;

import lombok.Data;

@Data
public class WorkBean {
    private int id;
    private String work;

    public WorkBean() {
    }

    public WorkBean(int id, String work) {
        this.id = id;
        this.work = work;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return "WorkBean{" +
                "id=" + id +
                ", work='" + work + '\'' +
                '}';
    }
}
