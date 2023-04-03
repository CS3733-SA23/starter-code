package edu.wpi.teamR.controllers;

import edu.wpi.teamR.fields.MealFields;
import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SortOrdersController {

  @FXML TableView requestTable;
  @FXML TableColumn nameColumn;
  @FXML TableColumn locationColumn;
  @FXML TableColumn staffMemberColumn;
  @FXML TableColumn notesColumn;
  @FXML TableColumn mealColumn;
  @FXML ChoiceBox sortListByBox;
  @FXML MFXButton homeButton;

  ObservableList<String> orderList = FXCollections.observableArrayList("Name", "Location", "Meal");

  @FXML
  public void initialize() {
    homeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    sortListByBox.setValue("Default");
    sortListByBox.setItems(orderList);
    nameColumn.setCellValueFactory(new PropertyValueFactory<MealFields, String>("Name"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<MealFields, String>("Location"));
    staffMemberColumn.setCellValueFactory(
        new PropertyValueFactory<MealFields, String>("Staff Member"));
    notesColumn.setCellValueFactory(new PropertyValueFactory<MealFields, String>("Notes"));
    mealColumn.setCellValueFactory(new PropertyValueFactory<MealFields, String>("Meal"));

    requestTable.setItems(getRequests());
  }

  public ObservableList<MealFields> getRequests() {
    ObservableList<MealFields> meals = FXCollections.observableArrayList();

    // test list
    meals.add(new MealFields("me", "here", "staff", "hi", "chicken"));
    meals.add(new MealFields("you", "there", "staff2", "bye", "beef"));
    meals.add(new MealFields("a", "a", "a", "a", "a"));
    meals.add(new MealFields("z", "z", "z", "z", "z"));

    return meals;
  }
}
