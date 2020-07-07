package com.order.handler;

import com.order.config.ThymeleafConfig;
import com.order.view.TemplatePresenter;
import com.order.view.Views;
import io.javalin.Javalin;
import io.javalin.http.Context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletResponse;
@ExtendWith(MockitoExtension.class)
class WelcomeHandlerTest {

    @Test
    void register() {
        Javalin javalin = Mockito.mock(Javalin.class);
        WelcomeHandler welcomeHandler = new WelcomeHandler(new TemplatePresenter(ThymeleafConfig.templateEngine()));
        welcomeHandler.register(javalin);
        Mockito.verify(javalin).get(Mockito.eq(Routes.WELCOME_ROUTE), Mockito.any(io.javalin.http.Handler.class));
    }
    private static final String TEXT_HTML = "text/html";
    private static final String CONTENT_TYPE = "Content-Type";
    @Test
    void test() {
        Context context = Mockito.mock(Context.class);
        TemplatePresenter templatePresenter = Mockito.mock(TemplatePresenter.class);
        Handler welcomeHandler = new WelcomeHandler(templatePresenter);

//        Mockito.when(templatePresenter.template(Views.WELCOME)).thenReturn("welcome");
        Mockito.when(context.header(Mockito.any(String.class),Mockito.any(String.class))).thenReturn(null);
        welcomeHandler.handle(context);
        Mockito.verify(context).result(Mockito.anyString());
    }
}