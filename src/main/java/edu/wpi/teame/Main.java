package edu.wpi.teame;

import edu.wpi.teame.Database.DatabaseController;
import edu.wpi.teame.Database.DatabaseGraphController;
import edu.wpi.teame.map.HospitalNode;

public class Main {

  public static void main(String[] args) {
    DatabaseController db = DatabaseController.INSTANCE;
    DatabaseGraphController graphController = new DatabaseGraphController(db);
    graphController.retrieveFromTable();
    HospitalNode.processEdgeList(graphController.getHospitalEdges());
    System.out.println(HospitalNode.allNodes);
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
