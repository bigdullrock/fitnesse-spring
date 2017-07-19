package com.bigdullrock.fitnesse.fixtures;

import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigdullrock.fitnesse.sut.GrantDao;
import com.bigdullrock.fitnesse.sut.ParticipantDao;

public class DataFixture {

  @Autowired
  private ParticipantDao participantDao;
  @Autowired
  private GrantDao grantDao;

  public DataFixture() {
    currentSpringContext().autowire(this);
  }

  public void resetData() {
    participantDao.reset();
    grantDao.reset();
  }
}
