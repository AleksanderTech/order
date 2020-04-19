package com.order.model;

import java.util.Date;

public class StartModel implements ViewModel<StartModel> {

    private String name;
    private Date date;

    public StartModel(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public StartModel model() {
        return this;
    }

    @Override
    public String toString() {
        return "StartModel{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
