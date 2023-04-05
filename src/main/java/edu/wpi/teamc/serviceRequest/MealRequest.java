package edu.wpi.teamc.serviceRequest;

import lombok.Getter;
import lombok.Setter;

public class MealRequest implements IServiceRequest {
  @Getter @Setter private Meal selection;
  @Getter @Setter private String room;
  @Getter @Setter private String specialNotes;
  @Getter @Setter private STATUS stat;

  public MealRequest(Meal select, String location, String note, STATUS stat) {
    this.selection = select;
    this.room = location;
    this.specialNotes = note;
    this.stat = stat;
  }
}
