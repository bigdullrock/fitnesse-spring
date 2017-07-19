package com.bigdullrock.fitnesse.fixtures;

import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigdullrock.fitnesse.sut.Grant;
import com.bigdullrock.fitnesse.sut.GrantDao;

public class GrantsQueryTable {

  @Autowired
  private GrantDao grantDao;

  private final Long participantNumber;

  public GrantsQueryTable() {
    this(null);
  }

  public GrantsQueryTable(Long participantNumber) {
    this.participantNumber = participantNumber;
    currentSpringContext().autowire(this);
  }

  public List<List<List<String>>> query() throws Exception {
    return queryGrants().stream()
        .map(g -> Arrays.asList(
            field("participant", g.participant.participantNumber),
            field("value", g.value),
            field("grant type", g.grantType),
            field("is funky", g.funky),
            field("comment", g.comment)))
        .collect(Collectors.toList());
  }

  private List<String> field(String column, Object value) {
    return Arrays.asList(column, String.valueOf(value));
  }

  private Collection<Grant> queryGrants() {
    if (participantNumber == null) {
      return grantDao.findAll();
    } else {
      return grantDao.getGrants(participantNumber);
    }
  }
}
