package edu.wpi.teamc.controllers;

import edu.wpi.teamc.MapDisplay.Table_Edit;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

public class CSV_EditController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private TableView<edu.wpi.teamc.MapDisplay.Table_Edit> Edit_Table;
  // @FXML TableView<edu.wpi.teamc.MapDisplay.Table_Edit> otherTable;
  @FXML TableColumn<edu.wpi.teamc.MapDisplay.Table_Edit, String> Column_longName;
  @FXML TableColumn<edu.wpi.teamc.MapDisplay.Table_Edit, String> Column_shortName;
  @FXML TableColumn<edu.wpi.teamc.MapDisplay.Table_Edit, String> Column_nodeType;
  ObservableList<edu.wpi.teamc.MapDisplay.Table_Edit> rows = FXCollections.observableArrayList();

  @FXML private TextField longNameBar;
  @FXML private TextField shortNameBar;
  @FXML private TextField nodeTypeBar;

  @FXML private Button goHome;

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  public void dispTable(javafx.event.ActionEvent actionEvent) throws IOException {
    Column_longName.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.teamc.MapDisplay.Table_Edit, String>("LongName"));

    Column_shortName.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.teamc.MapDisplay.Table_Edit, String>("ShortName"));

    Column_nodeType.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.teamc.MapDisplay.Table_Edit, String>("NodeType"));

    Edit_Table.getItems().setAll(getTableEdit());

    Column_longName.setCellFactory(TextFieldTableCell.forTableColumn());
    Column_shortName.setCellFactory(TextFieldTableCell.forTableColumn());
    Column_nodeType.setCellFactory(TextFieldTableCell.forTableColumn());

    System.out.println("Check-Edit");
  }

  public ObservableList<edu.wpi.teamc.MapDisplay.Table_Edit> getTableEdit() throws IOException {
    rows.add(new edu.wpi.teamc.MapDisplay.Table_Edit("Hall 1 Level 2", "Hall", "HALL"));
    rows.add(new Table_Edit("Room One Zero One", "Room 101", "ConferenceRoom"));
    /*for (String[] row : readData()) {
      rows.add(new Table_Edit(row[0], row[1], row[2]));
    } */

    return rows;
  }

  @FXML
  public void submit(javafx.event.ActionEvent actionEvent) {
    ObservableList<Table_Edit> currentTable = Edit_Table.getItems();
    String currentPlace = longNameBar.getText();

    for (Table_Edit te : currentTable) {
      if (Objects.equals(te.getLongName(), currentPlace)) {
        te.setLongName(longNameBar.getText());
        te.setShortName(shortNameBar.getText());
        te.setNodeType(nodeTypeBar.getText());

        Edit_Table.setItems(currentTable);
        Edit_Table.refresh();
        break;
      }
    }
  }

  @FXML
  void rowClicked(MouseEvent event) {
    Table_Edit clickedAnimal = Edit_Table.getSelectionModel().getSelectedItem();
    longNameBar.setText(String.valueOf(clickedAnimal.getLongName()));
    shortNameBar.setText(String.valueOf(clickedAnimal.getShortName()));
    nodeTypeBar.setText(String.valueOf(clickedAnimal.getNodeType()));
  }
  //  @Override
  //  public void initialize(URL location, ResourceBundle resources) {
  //
  //  }

  //  @FXML
  //  public String getText(ActionEvent event) {
  //    String inputtedText;
  //    inputtedText = textField.getText();
  //    return inputtedText;
  //  }

  public String getText(javafx.event.ActionEvent actionEvent) {
    String inputtedText;
    inputtedText = inputBox.getText();
    return inputtedText;
  }
  //  public void createTable() {
  //    String test = "testing";
  //    ColLocation = ;
  //
  //  }

  // This will be use to read the csv file in later prototypes, somehow I can't figure out the
  // actual file address for Loaction.csv
  public List<String[]> readData() throws IOException {
    int count = 0;
    String file = "Location.csv";
    List<String[]> content = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line = "";
      while ((line = br.readLine()) != null) {
        content.add(line.split(","));
      }
    } catch (FileNotFoundException e) {
      // Some error logging
    }
    return content;
  }
}
