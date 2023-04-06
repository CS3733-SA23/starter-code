package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.database.DAOImps.NodeDAOImp;
import edu.wpi.teamA.database.ORMclasses.Node;
import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import edu.wpi.teamA.pathfinding.AStar;
import edu.wpi.teamA.pathfinding.SearchController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PathfindingController implements IPageController {

  @FXML private MFXFilterComboBox<Integer> startSelection;
  @FXML private MFXFilterComboBox<Integer> endSelection;
  @FXML private Text directions;
  @FXML MFXButton submitButton;
  private ArrayList<Integer> nodeOptions = new ArrayList<Integer>();
  private NodeDAOImp nodeDAO = new NodeDAOImp();
  private SearchController search;

  @Override
  public void initialize() {
    ArrayList<Node> nodeList = nodeDAO.loadNodesFromDatabase();

    for (Node node : nodeList) {
      nodeOptions.add(node.getNodeID());
    }

    startSelection.setItems(FXCollections.observableArrayList(nodeOptions));
    endSelection.setItems(FXCollections.observableArrayList(nodeOptions));
  }

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }

  public void submit() {
    try {
      AStar a = new AStar(startSelection.getSelectedItem(), endSelection.getSelectedItem());
      directions.setText(a.toString());
      System.out.println("Nodes submitted");
      System.out.println(a);
    } catch (NullPointerException e) {
      System.out.println("Null Value");
    }
  }
}
