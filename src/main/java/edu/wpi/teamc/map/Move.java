package edu.wpi.teamc.map;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Move {
  private String nodeID;
  private String longName;
  private Date date;

  public Move(String nodeID, String longName, Date date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }
}
