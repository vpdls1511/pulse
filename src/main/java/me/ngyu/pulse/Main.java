package me.ngyu.pulse;

import me.ngyu.pulse.core.container.BeanContainer;
import me.ngyu.pulse.core.handler.HandlerMapper;
import me.ngyu.pulse.core.scanner.ComponentScanner;
import me.ngyu.pulse.core.scanner.ControllerScanner;
import me.ngyu.pulse.core.scanner.DependencyInjector;
import me.ngyu.pulse.server.PulseServer;

public class Main {

  private static final String BASE_PACKAGE = "me.ngyu.pulse";

  public static void main(String[] args) throws Exception {
    BeanContainer beancontainer = new BeanContainer();

    ComponentScanner scanner = new ComponentScanner(beancontainer);
    scanner.scan(BASE_PACKAGE);

    DependencyInjector injector = new DependencyInjector(beancontainer);
    injector.inject();

    HandlerMapper handlerMapper = new HandlerMapper();
    ControllerScanner controllerScanner = new ControllerScanner(beancontainer, handlerMapper);

    controllerScanner.scan();

    System.out.println("\uD83D\uDCE6 Registered Beans: ");
    beancontainer.getAllBeans().forEach((clazz, instance) -> {
      System.out.println("- " + clazz.getSimpleName());
    });

    new PulseServer(8080, beancontainer, handlerMapper).run();
  }
}
