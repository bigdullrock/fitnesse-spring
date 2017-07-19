package com.bigdullrock.fitnesse.spring;

import java.util.Arrays;
import java.util.function.Function;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * This class is called from Fitnesse to create a SpringContext.
 * The context can be created with annotated
 * <code>{@link Configuration @Configuration}</code> classes,
 * a list of <code>basePackages</code> to scan,
 * or a list of <code>xml</code> files. 
 */
public final class SpringContextFactory {

  private static final Object MUTEX = new Object();

  private static SpringContext springContext;

  /**
   * Get the current {@link SpringContext}. If it has not been created by an earlier SetUp
   * in Fitnesse, you will get an error.
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
   * <code>{@link Configuration @Configuration}</code> classes.
   */
  public SpringContext createSpringContextAnnotatedClasses(String... annotatedClasses) {
    synchronized (MUTEX) {
      if (springContext == null) {
        Class<?>[] classes =
            Arrays.asList(annotatedClasses).stream().map(strToClass).toArray(Class<?>[]::new);
        springContext = new SpringContext(new AnnotationConfigApplicationContext(classes));
        return springContext;
      } else {
        return springContext;
      }
    }
  }

  /**
   * Initialize a {@link SpringContext} with a list of packages to scan.
   */
  public SpringContext createSpringContextBasePackages(String... basePackages) {
    synchronized (MUTEX) {
      if (springContext == null) {
        springContext = new SpringContext(new AnnotationConfigApplicationContext(basePackages));
        return springContext;
      } else {
        return springContext;
      }
    }
  }

  /**
   * Initialize a {@link SpringContext} with a list of xml files.
   */
  public SpringContext createSpringContextResourceLocations(String... resourceLocations) {
    synchronized (MUTEX) {
      if (springContext == null) {
        springContext = new SpringContext(new GenericXmlApplicationContext(resourceLocations));
        return springContext;
      } else {
        return springContext;
      }
    }
  }

  private static Function<String, Class<?>> strToClass = str -> {
    try {
      return Class.forName(str);
    } catch (ClassNotFoundException e1) {
      throw new IllegalStateException(
          "SpringContext not initialized - unknown Annotated Class", e1);
    }
  };
}
