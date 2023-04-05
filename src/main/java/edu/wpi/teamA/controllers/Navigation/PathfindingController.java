package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.database.DAOImps.NodeDAOImp;
import edu.wpi.teamA.database.ORMclasses.Node;
import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import edu.wpi.teamA.pathfinding.BFS;
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
  @FXML private MFXButton submitButton;
  private ArrayList<Integer> nodeOptions = new ArrayList<Integer>();
  private NodeDAOImp nodeDAO = new NodeDAOImp();

  @Override
  public void initialize() {
    ArrayList<Node> nodeList = nodeDAO.loadNodesFromDatabase();

    for (Node node : nodeList) {
      nodeOptions.add(node.nodeID);
    }

    startSelection.setItems(FXCollections.observableArrayList(nodeOptions));
    endSelection.setItems(FXCollections.observableArrayList(nodeOptions));
  }

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }

  public void submit() {
    BFS bfs = new BFS(startSelection.getSelectedItem(), startSelection.getSelectedItem());
    directions.setText(bfs.toString());
    System.out.println("Nodes submitted");
  }
}
