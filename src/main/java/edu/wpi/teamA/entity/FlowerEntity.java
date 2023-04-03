package edu.wpi.teamA.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

public class FlowerEntity {
  @Getter @Setter private String name;
  @Getter @Setter private int room;
  @Getter @Setter private Date date;
  @Getter @Setter private int time;
  @Getter @Setter private String flowerType;
  @Getter @Setter private String comment;

  FlowerEntity(String a, int b, Date c, int d, String e, String f) {
    this.name = a;
    this.room = b;
    this.date = c;
    this.time = d;
    this.flowerType = e;
    this.comment = f;
  }
}
