package edu.wpi.teamA.database.ORMclasses;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class Move {
  @Getter @Setter private Integer nodeID;

  @Getter @Setter private String longName;

  @Getter @Setter private LocalDate date;

  public Move(Integer nodeID, String longName, LocalDate date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }
}
