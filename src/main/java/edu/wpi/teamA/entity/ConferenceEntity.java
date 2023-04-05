package edu.wpi.teamA.entity;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

public class ConferenceEntity {
  @Getter @Setter private String name;
  @Getter @Setter private int room;
  @Getter @Setter private Date date;
  @Getter @Setter private int startTime;
  @Getter @Setter private int endTime;
  @Getter @Setter private String comment;

  ConferenceEntity(String a, int b, Date c, int d, int e, String f) {
    this.name = a;
    this.room = b;
    this.date = c;
    this.startTime = d;
    this.endTime = e;
    this.comment = f;
  }
}
