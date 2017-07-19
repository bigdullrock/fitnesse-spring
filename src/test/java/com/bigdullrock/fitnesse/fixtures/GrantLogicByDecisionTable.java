package com.bigdullrock.fitnesse.fixtures;

import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigdullrock.fitnesse.sut.GrantCalculationService;
import com.bigdullrock.fitnesse.sut.GrantType;

public class GrantLogicByDecisionTable {

  @Autowired
  private GrantCalculationService grantCalculationService;

  private int age;
  private GrantType grantType;
  private int value;

  public GrantLogicByDecisionTable() {
    currentSpringContext().autowire(this);
  }

  public void reset() throws Exception {
    this.age = 16;
    this.grantType = GrantType.FOO;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGrantType(GrantType grantType) {
    this.grantType = grantType;
  }

  public void execute() throws Exception {
    this.value = grantCalculationService.determineAmount(age, grantType);
  }

  public int value() {
    return value;
  }
}
