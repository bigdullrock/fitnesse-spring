package com.bigdullrock.fitnesse.fixtures;

import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigdullrock.fitnesse.sut.GrantCalculationService;
import com.bigdullrock.fitnesse.sut.GrantType;

public class GrantLogicByScriptTable {

  @Autowired
  private GrantCalculationService grantCalculationService;

  public GrantLogicByScriptTable() {
    currentSpringContext().autowire(this);
  }

  public int grantValueForParticipantAgedForType(int age, GrantType type) {
    return grantCalculationService.determineAmount(age, type);
  }
}
