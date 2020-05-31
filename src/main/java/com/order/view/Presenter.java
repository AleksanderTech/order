package com.order.view;

import com.order.view.model.*;

public interface Presenter {

    String template(String name);

    String template(String name, ViewModel<?> viewModel);
}
