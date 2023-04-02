package edu.wpi.teame.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DatabaseViewController {
    @FXML
    TableView<String> dataTable;

    @FXML
    TableColumn nodeIDCol;

    @FXML
    TableColumn nameCol;

    @FXML
    TableColumn dateCol;
}
