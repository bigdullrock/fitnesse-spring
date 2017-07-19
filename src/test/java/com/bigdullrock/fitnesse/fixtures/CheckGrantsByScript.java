package com.bigdullrock.fitnesse.fixtures;

import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigdullrock.fitnesse.sut.GrantDao;

public class CheckGrantsByScript {

  @Autowired
  private GrantDao grantDao;

  public CheckGrantsByScript() {
    currentSpringContext().autowire(this);
  }

  public int numberOfGrantsForParticipant(Long participantNumber) {
    return grantDao.getGrants(participantNumber).size();
  }

  public int valueOfGrantForParticipant(int index, Long participantNumber) {
    return grantDao.getGrants(participantNumber).get(index - 1).value;
  }
}
