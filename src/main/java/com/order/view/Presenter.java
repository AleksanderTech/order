package com.order.view;

import com.order.model.ViewModel;

public interface Presenter {

    String template(String name);

    String template(String name, ViewModel<?> viewModel);
}
