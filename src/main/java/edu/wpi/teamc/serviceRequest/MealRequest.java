package edu.wpi.teamc.serviceRequest;

public class MealRequest implements IServiceRequest {

  private Meal selection;
  private String room;
  private String specialNotes;
  private STATUS stat;

  public String getStat() {
    return stat.name();
  }

  public void setStat(STATUS stat) {
    this.stat = stat;
  }

  public MealRequest(Meal select, String location, String note, STATUS stat) {
    this.selection = select;
    this.room = location;
    this.specialNotes = note;
    this.stat = stat;
  }

  public Meal getSelection() {
    return selection;
  }

  public void setSelection(Meal selection) {
    this.selection = selection;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public String getSpecialNotes() {
    return specialNotes;
  }

  public void setSpecialNotes(String specialNotes) {
    this.specialNotes = specialNotes;
  }
}
