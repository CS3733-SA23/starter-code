package edu.wpi.teame.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseViewController {
    @FXML
    TableView<MapInitializer> dataTable;

    @FXML
    TableColumn<MapInitializer, String> nodeIDCol;

    @FXML
    TableColumn<MapInitializer, String> nameCol;

    @FXML
    TableColumn<MapInitializer, String> dateCol;


    @FXML
    public void initialize(){
        //load the database into the table
        nodeIDCol.setCellValueFactory(new PropertyValueFactory<MapInitializer,String>("nodeID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<MapInitializer, String>("longName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<MapInitializer, String>("date"));

        List itemList = new ArrayList<>(); //REPLACE WITH THE METHOD CALL

        dataTable.setItems();

        dataTable.setEditable(true);
    }
}
