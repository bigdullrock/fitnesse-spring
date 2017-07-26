package com.bigdullrock.fitnesse.spring;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The factory classes create an instance of this class during initialization of Fitnesse.
 * A call to the factory is run before any Suite tests are run. Then the
 * factory is called from the constructor of each fixture and the factory calls {@link #autowire}.
 */
public class SpringContext {

  private final ConfigurableApplicationContext context;

  public SpringContext(ConfigurableApplicationContext context) {
    this.context = context;
  }

  /**
   * This does not need to be called by the SpringBootContextHolderFactory, it refreshes on startup.
   */
  public void initialize() {
    if (!this.context.isActive()) {
      this.context.refresh();
    }
  }

  /**
   * Autowire an object, specifically a FitNesse Fixture. The <code>dependencyCheck</code> flag is
   * set to false on purpose. If it were true, when the Fixture class tries to
   * <code>autowire(this)</code>, it will try to autowire all setter methods. Since Fitnesse
   * necessitates having the setter methods, this will cause the Spring initialization to fail
   * because it cannot find those dependencies.
   *
   * @param toAutowire the bean to autowire
   */
  public void autowire(Object toAutowire) {
    context.getAutowireCapableBeanFactory().autowireBeanProperties(toAutowire,
        AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
    context.getAutowireCapableBeanFactory().initializeBean(toAutowire,
        toAutowire.getClass().getName());
  }

  public Object getBean(String name) {
    return context.getBean(name);
  }

  public <T> T getBean(Class<T> beanType) {
    return context.getBean(beanType);
  }

  public ConfigurableApplicationContext getContext() {
    return context;
  }

  /**
   * Close the Spring Context.
   */
  public void close() {
    getContext().close();
  }

}
