package edu.wpi.teamc;

import edu.wpi.teamc.map.*;
import java.sql.SQLException;

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
