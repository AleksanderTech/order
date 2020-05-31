package com.order.view;

import com.order.config.ThymeleafConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TemplatePresenterTest {

    @Test
    void template_templateExists_returnsTemplate() {
        var presenter = new TemplatePresenter(ThymeleafConfig.templateEngine());
        assertNotNull(presenter.template(Views.WELCOME));
    }
}