package edu.wpi.teamR.fields;

import java.util.*;

public class ServiceRequestData {
  private ArrayList<MealFields> mealData;
  private ArrayList<FurnitureFields> furnitureData;

  public ServiceRequestData() {
    this.mealData = new ArrayList<MealFields>();
    this.furnitureData = new ArrayList<FurnitureFields>();
  }

  public void addMealData(MealFields newField) {
    mealData.add(newField);
  }

  public void addFurnitureData(FurnitureFields newField) {
    furnitureData.add(newField);
  }
}
