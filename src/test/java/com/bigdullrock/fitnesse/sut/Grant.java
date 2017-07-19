package com.bigdullrock.fitnesse.sut;

public class Grant {

  public Participant participant;
  public GrantType grantType;
  public int value;
  public boolean funky;
  public String comment;

  public Grant(Participant participant, GrantType grantType, int value) {
    this.grantType = grantType;
    this.participant = participant;
    this.value = value;
    this.funky = false;
    this.comment =
        "Some comment for a grant for " + participant.firstName + " " + participant.lastName;
  }
}
