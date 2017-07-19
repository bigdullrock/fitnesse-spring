package com.bigdullrock.fitnesse.sut;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ParticipantDao {

  private int currentId = 10841;

  private List<Participant> participants;

  public void reset() {
    participants = new ArrayList<Participant>();
  }

  public void persist(Participant participant) {
    if (participant.participantNumber == null) {
      participant.participantNumber = getUniqueId();
    }
    participants.add(participant);
  }

  public long getUniqueId() {
    return currentId++;
  }

  public List<Participant> getParticipants() {
    return participants;
  }
}
