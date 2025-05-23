package ru.netology.initializer;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.netology.config.WebConfig;
import ru.netology.controller.PostController;

import javax.servlet.ServletContext;

public class ApplicationInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext servletContext) {
    final var context = new AnnotationConfigWebApplicationContext();
    context.scan("ru.netology");
    //context.register(WebConfig.class);
    //context.register(PostController.class);
    context.refresh();

    final var servlet = new DispatcherServlet(context);
    final var registration = servletContext.addServlet(
            "app", servlet
    );
    registration.setLoadOnStartup(1);
    registration.addMapping("/");
  }
}
