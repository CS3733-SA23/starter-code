package edu.wpi.teamc.serviceRequest;

import java.util.ArrayList;
import java.util.List;

public class Meal {
  public String mealName;
  public String mealDescription;
  public int mealCalories;
  List<String> mealIngredient = new ArrayList<String>();

  public Meal(String name, String description, int calories, List<String> ingredient) {
    mealName = name;
    mealDescription = description;
    mealCalories = calories;
    mealIngredient = ingredient;
  }
}
