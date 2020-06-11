package com.order.view.model;

import java.util.Map;

public class HomeModel implements ViewModel<HomeModel> {

    public Map<String,String> cookies;
    public long userId;

    public HomeModel(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public HomeModel(Map<String, String> cookies, long userId) {
        this.cookies = cookies;
        this.userId = userId;
    }

    @Override
    public HomeModel model() {
        return this;
    }
}
