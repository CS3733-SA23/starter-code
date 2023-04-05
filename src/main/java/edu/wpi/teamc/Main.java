package edu.wpi.teamc;

import edu.wpi.teamc.map.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws ClassNotFoundException, SQLException {

    Cdb.loadDatabaseTables(
        Cdb.databaseNodeList,
        Cdb.databaseEdgeList,
        Cdb.databaseLocationNameList,
        Cdb.databaseMoveList);

    CApp.launch(CApp.class, args);
  }
}
