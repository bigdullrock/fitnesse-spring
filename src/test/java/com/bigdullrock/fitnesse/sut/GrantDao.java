package com.bigdullrock.fitnesse.sut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class GrantDao {

  private List<Grant> grants;

  public void reset() {
    grants = new ArrayList<>();
  }

  public List<Grant> getGrants(final Long participantNumber) {
    return grants.stream()
        .filter(g -> g.participant.participantNumber.equals(participantNumber))
        .collect(Collectors.toList());
  }

  public void persist(Grant grant) {
    grants.add(grant);
  }

  public Collection<Grant> findAll() {
    return grants;
  }
}
