package com.bigdullrock.fitnesse.spring;

import org.springframework.boot.SpringApplication;

/**
 * This class is called from Fitnesse to create an
 * {@link org.springframework.context.ApplicationContext @ApplicationContext} that is wired by
 * Spring Boot. The class that is passed will be one with the
 * <code>{@link org.springframework.boot.autoconfigure.SpringBootApplication @SpringBootApplication}</code>
 * annotation.
 */
public final class SpringBootContextFactory {

  private static final Object MUTEX = new Object();

  private static SpringContext springContext;

  /**
   * Default constructor that does not set an active profile.
   */
  public SpringBootContextFactory() {}

  /**
   * Constructor that takes an comma delimited list of activeProfiles so that Spring Boot picks up
   * the correct properties.
   * 
   * @param activeProfiles Comma delimited String of the active profiles.
   */
  public SpringBootContextFactory(String activeProfiles) {
    System.setProperty("spring.profiles.active", activeProfiles);
  }

  /**
   * Get the current {@link SpringContext}. If it has not been created by an earlier SetUp in
   * Fitnesse, you will get an error.
   * 
   * @return SpringContext the active and initialized
   *         {@link org.springframework.context.ConfigurableApplicationContext @ConfigurableApplicationContext}.
   */
  public static SpringContext currentSpringContext() {
    synchronized (MUTEX) {
      if (springContext == null) {
        throw new IllegalStateException("SpringBootContextHolder should be initialized first");
      }
      return springContext;
    }
  }

  /**
   * Initialize a {@link SpringContext} with an annotated
   * <code>{@link org.springframework.boot.autoconfigure.SpringBootApplication @SpringBootApplication}</code>
   * class.
   *
   * @param bootClassName main class of your Spring Boot application
   * @return SpringContext the active and initialized
   *         {@link org.springframework.context.ConfigurableApplicationContext @ConfigurableApplicationContext}.
   */
  public SpringContext createSpringContext(String bootClassName) {
    synchronized (MUTEX) {
      if (springContext == null) {
        try {
          return new SpringContext(
              SpringApplication.run(Class.forName(bootClassName), new String[] {}));
        } catch (ClassNotFoundException ex) {
          throw new IllegalStateException(
              "SpringContext not initialized - unknown Annotated Class", ex);
        }
      } else {
        return springContext;
      }
    }
  }

}
