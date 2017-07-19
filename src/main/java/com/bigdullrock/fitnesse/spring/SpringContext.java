package com.bigdullrock.fitnesse.spring;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

/**
 * This class is created by the {@link SpringContextFactory} during initialization of Fitnesse.
 * A call to the factory is run before any Suite tests are run. Then the factory is called
 * from the constructor of each fixture and the factory calls {@link #autowire}.
 */
public class SpringContext {

  private final GenericApplicationContext context;

  public SpringContext(GenericApplicationContext context) {
    this.context = context;
  }

  /**
   * Autowire an object, specifically a FitNesse Fixture.
   *
   * @param toAutowire the bean to autowire
   */
  public void autowire(Object toAutowire) {
    context.getBeanFactory().autowireBeanProperties(toAutowire,
        AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
    context.getBeanFactory().initializeBean(toAutowire, toAutowire.getClass().getName());
  }

  public Object getBean(String name) {
    return context.getBean(name);
  }

  public <T> T getBean(Class<T> beanType) {
    return context.getBean(beanType);
  }

  /**
   * Close the Spring Context.
   */
  public void close() {
    context.close();
  }
}
