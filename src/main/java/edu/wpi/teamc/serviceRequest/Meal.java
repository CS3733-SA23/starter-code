package edu.wpi.teamc.serviceRequest;

public class Meal {
  private String mealName;

  private String addtionalInfo;

  // Should it be this.mealName = mealName? and this.addtionalInfo = addtionalInfo?
  public Meal(String name, String notes) {
    this.mealName = name;
    this.addtionalInfo = notes;
  }
}
