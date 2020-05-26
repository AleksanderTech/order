package com.order.model;

import java.util.Date;

public class WelcomeModel implements ViewModel<WelcomeModel> {

    private String name;
    private Date date;

    public WelcomeModel(String name, Date date) {
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
    public WelcomeModel model() {
        return this;
    }

    @Override
    public String toString() {
        return "WelcomeModel{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
