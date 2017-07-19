package com.bigdullrock.fitnesse.fixtures;

import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigdullrock.fitnesse.sut.GrantCalculationService;

public class LogicFixture {

  @Autowired
  private GrantCalculationService grantCalculationService;

  public LogicFixture() {
    currentSpringContext().autowire(this);
  }

  public void whenWeCalculateTheGrants() {
    grantCalculationService.calculateGrants();
  }
}
