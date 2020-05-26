package com.order.view;

import com.order.config.ThymeleafConfig;
import com.order.model.WelcomeModel;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TemplatePresenterTest {

    @Test
    void template_templateExists_returnsTemplate() {
        var presenter = new TemplatePresenter(ThymeleafConfig.templateEngine());
        assertNotNull(presenter.template(Views.WELCOME, new WelcomeModel("", new Date())));
    }
}