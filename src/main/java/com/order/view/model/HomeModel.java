package com.order.view.model;

import java.util.Map;

public class HomeModel implements ViewModel<HomeModel> {

    public Map<String,String> cookies;

    public HomeModel(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    public HomeModel model() {
        return this;
    }
}
