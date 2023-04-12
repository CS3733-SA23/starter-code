package edu.wpi.teame;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.HospitalNode;

public class Main {

  public static void main(String[] args) {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.getNodeList();
    HospitalNode.processEdgeList(SQLRepo.INSTANCE.getEdgeList());
    System.out.println(HospitalNode.allNodes);
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
