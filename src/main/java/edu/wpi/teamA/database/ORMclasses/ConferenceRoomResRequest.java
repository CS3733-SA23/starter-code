package edu.wpi.teamA.database.ORMclasses;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

public class ConferenceRoomResRequest {
  @Getter @Setter private String name;
  @Getter @Setter private int room;
  @Getter @Setter private Date date;
  @Getter @Setter private int startTime;
  @Getter @Setter private int endTime;
  @Getter @Setter private String comment;
  @Getter @Setter private String status;

  public ConferenceRoomResRequest(String a, int b, Date c, int d, int e, String f) {
    this.name = a;
    this.room = b;
    this.date = c;
    this.startTime = d;
    this.endTime = e;
    this.comment = f;
    this.status = "new";
  }
}
