package edu.wpi.teamc.serviceRequest;

public class Meal {
  private String mealName;

  private String addtionalInfo;

  // Should it be this.mealName = mealName? and this.addtionalInfo = addtionalInfo?
  public Meal(String name, String notes) {
    this.mealName = name;
    this.addtionalInfo = notes;
  }

  public String getMealName() {
    return mealName;
  }

  public void setMealName(String mealName) {
    this.mealName = mealName;
  }

  public String getAddtionalInfo() {
    return addtionalInfo;
  }

  public void setAddtionalInfo(String addtionalInfo) {
    this.addtionalInfo = addtionalInfo;
  }
}
