package edu.wpi.teamname.system;

import edu.wpi.teamname.database.DataManager;

import java.io.IOException;
import java.sql.SQLException;

import edu.wpi.teamname.navigation.Move;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.ParseException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {
    // Singleton is in DataManager, function has it so it will only return one connection when
    // called
    DataManager.configConnection(
        "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
        "teamd",
        "teamd40");
    App.launch(App.class, args);

  }

  // shortcut: psvm
}
