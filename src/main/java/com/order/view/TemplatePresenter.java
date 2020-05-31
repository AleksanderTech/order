package com.order.view;

import com.order.view.model.ViewModel;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

public class TemplatePresenter implements Presenter {

    public static final String EXTENSION = ".html";
    public static final String MODEL = "model";
    private final ITemplateEngine templateEngine;

    public TemplatePresenter(ITemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String template(String name) {
        return templateEngine.process(viewName(name), new Context());
    }

    @Override
    public String template(String name, ViewModel<?> viewModel) {
        Context context = new Context();
        context.setVariable(MODEL, viewModel);
        return templateEngine.process(viewName(name), context);
    }

    private String viewName(String name) {
        return name + EXTENSION;
    }
}
