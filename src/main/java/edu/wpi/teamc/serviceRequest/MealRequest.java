package edu.wpi.teamc.serviceRequest;

public class MealRequest {

  private Meal selection;
  private String room;
  private String specialNotes;

  public MealRequest(Meal select, String location, String note) {
    this.selection = select;
    this.room = location;
    this.specialNotes = note;
  }
}
