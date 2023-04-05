package edu.wpi.teamc.serviceRequest;

import lombok.Getter;
import lombok.Setter;

public class Meal {
  @Getter
  @Setter
  private String mealName;
@Getter
@Setter
  private String addtionalInfo;

  // Should it be this.mealName = mealName? and this.addtionalInfo = addtionalInfo?
  public Meal(String name, String notes) {
    this.mealName = name;
    this.addtionalInfo = notes;
  }

}
