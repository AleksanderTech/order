package com.order.view.model;

import java.util.Map;

public class HomeVM implements ViewModel<HomeVM> {

    public Map<String,String> cookies;
    public long userId;

    public HomeVM(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public HomeVM(Map<String, String> cookies, long userId) {
        this.cookies = cookies;
        this.userId = userId;
    }

    @Override
    public HomeVM model() {
        return this;
    }
}
