package com.bigdullrock.fitnesse.spring;

import java.util.Arrays;
import java.util.function.Function;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

/**
 * This class is called from Fitnesse to create a SpringContext. The context can be created with
 * annotated
 * <code>{@link org.springframework.context.annotation.Configuration @Configuration}</code> classes,
 * a list of <code>basePackages</code> to scan, or a list of <code>xml</code> files.
 */
public final class SpringContextFactory {

  private static final Object MUTEX = new Object();

  private final ConfigurableEnvironment env;

  private static SpringContext springContext;

  public SpringContextFactory(String activeProfile) {
    this.env = createEnv(activeProfile);
  }

  /**
   * Get the current {@link SpringContext}. If it has not been created by an earlier SetUp in
   * Fitnesse, you will get an error.
   * 
   * @return SpringContext the active and initialized
   *         {@link org.springframework.context.ApplicationContext @ApplicationContext}.
   */
  public static SpringContext currentSpringContext() {
    synchronized (MUTEX) {
      if (springContext == null) {
        throw new IllegalStateException("Spring context should be initialized first");
      }
      return springContext;
    }
  }

  /**
   * Initialize a {@link SpringContext} with a list of annotated
   * <code>{@link org.springframework.context.annotation.Configuration @Configuration}</code>
   * classes.
   *
   * @param annotatedClasses array of @Configuration class names.
   * @return SpringContext the active and initialized
   *         {@link org.springframework.context.ApplicationContext @ApplicationContext}.
   */
  public SpringContext createSpringContextAnnotatedClasses(String... annotatedClasses) {
    synchronized (MUTEX) {
      if (springContext == null) {
        Class<?>[] classes =
            Arrays.asList(annotatedClasses).stream().map(strToClass).toArray(Class<?>[]::new);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.setEnvironment(env);
        ctx.register(classes);
        springContext = new SpringContext(ctx);
        springContext.initialize();
        return springContext;
      } else {
        return springContext;
      }
    }
  }

  /**
   * Initialize a {@link SpringContext} with a list of packages to scan.
   *
   * @param basePackages array of packages to scan.
   * @return SpringContext the active and initialized
   *         {@link org.springframework.context.ApplicationContext @ApplicationContext}.
   */
  public SpringContext createSpringContextBasePackages(String... basePackages) {
    synchronized (MUTEX) {
      if (springContext == null) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.setEnvironment(env);
        ctx.scan(basePackages);
        springContext = new SpringContext(ctx);
        springContext.initialize();
        return springContext;
      } else {
        return springContext;
      }
    }
  }

  /**
   * Initialize a {@link SpringContext} with a list of xml files.
   *
   * @param resourceLocations array of xml file locations.
   * @return SpringContext the active and initialized
   *         {@link org.springframework.context.ApplicationContext @ApplicationContext}.
   */
  public SpringContext createSpringContextResourceLocations(String... resourceLocations) {
    synchronized (MUTEX) {
      if (springContext == null) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.setEnvironment(env);
        ctx.load(resourceLocations);
        springContext = new SpringContext(ctx);
        springContext.initialize();
        return springContext;
      } else {
        return springContext;
      }
    }
  }

  private static Function<String, Class<?>> strToClass = str -> {
    try {
      return Class.forName(str);
    } catch (ClassNotFoundException ex) {
      throw new IllegalStateException(
          "SpringContext not initialized - unknown Annotated Class", ex);
    }
  };

  private ConfigurableEnvironment createEnv(String activeProfile) {
    StandardEnvironment env = new StandardEnvironment();
    env.addActiveProfile(activeProfile);
    env.setActiveProfiles(activeProfile);
    env.setDefaultProfiles(activeProfile);
    return env;
  }
}
