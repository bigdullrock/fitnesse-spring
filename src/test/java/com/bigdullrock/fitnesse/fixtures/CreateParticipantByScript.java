package com.bigdullrock.fitnesse.fixtures;

import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigdullrock.fitnesse.sut.Participant;
import com.bigdullrock.fitnesse.sut.ParticipantDao;

public class CreateParticipantByScript {

  @Autowired
  private ParticipantDao participantDao;

  private Participant participant;

  public CreateParticipantByScript() {
    currentSpringContext().autowire(this);
  }

  public void newParticipant() {
    participant = new Participant();
    participantDao.persist(participant);
  }

  private Participant getParticipant() {
    if (participant == null) {
      newParticipant();
    }
    return participant;
  }

  public void setFirstName(String firstName) {
    getParticipant().firstName = firstName;
  }

  public void setLastName(String lastName) {
    participant.lastName = lastName;
  }

  public Long getParticipantNumber() {
    return participant.participantNumber;
  }

}
