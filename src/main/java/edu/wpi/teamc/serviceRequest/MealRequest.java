package edu.wpi.teamc.serviceRequest;

public class MealRequest {
  public Meal selection;
  public int deliveryLocation;
  public String specialNotes;

  public MealRequest(Meal select, int location, String note) {
    select = selection;
    location = deliveryLocation;
    note = specialNotes;
  }
}
