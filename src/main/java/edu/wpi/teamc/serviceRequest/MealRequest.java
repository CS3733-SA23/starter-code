package edu.wpi.teamc.serviceRequest;

import lombok.Getter;
import lombok.Setter;

public class MealRequest implements IServiceRequest {
  @Getter @Setter private Requester requester;
  @Getter @Setter private Meal meal;
  @Getter @Setter private String room;
  @Getter @Setter private String specialNotes;
  @Getter @Setter private STATUS stat;

  public MealRequest(Requester requester, Meal meal, String location, String note, STATUS stat) {
    this.requester = requester;
    this.meal = meal;
    this.room = location;
    this.specialNotes = note;
    this.stat = stat;
  }
}
