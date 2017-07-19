package com.bigdullrock.fitnesse.fixtures;

import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigdullrock.fitnesse.sut.Participant;
import com.bigdullrock.fitnesse.sut.ParticipantDao;

public class CreateParticipantsByDecisionTable {

  @Autowired
  private ParticipantDao participantDao;

  private Participant participant;

  public CreateParticipantsByDecisionTable() {
    currentSpringContext().autowire(this);
  }

  public void reset() throws Exception {
    participant = new Participant();
  }

  public void setFirstName(String firstName) {
    participant.firstName = firstName;
  }

  public void setLastName(String lastName) {
    participant.lastName = lastName;
  }

  public void execute() throws Exception {
    participantDao.persist(participant);
  }

  public Long participantNumber() {
    return participant.participantNumber;
  }
}
