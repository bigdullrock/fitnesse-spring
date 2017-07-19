package com.bigdullrock.fitnesse.sut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrantCalculationService {

  @Autowired
  private ParticipantDao participantDao;
  @Autowired
  private GrantDao grantDao;

  public void calculateGrants() {
    for (Participant participant : participantDao.getParticipants()) {
      for (GrantType type : GrantType.values()) {
        grantDao.persist(calculateGrant(participant, type));
      }
    }
  }

  private Grant calculateGrant(Participant participant, GrantType type) {
    return new Grant(participant, type, determineAmount(participant.age, type));
  }

  public int determineAmount(int age, GrantType type) {
    switch (type) {
      case FOO:
        return 200;
      case BAR:
        return 300;
      default:
        return 0;
    }
  }
}
