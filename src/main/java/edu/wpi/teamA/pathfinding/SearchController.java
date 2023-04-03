package edu.wpi.teamA.pathfinding;

import edu.wpi.teamA.database.Edge;
import edu.wpi.teamA.database.Node;
import java.util.ArrayList;

public class SearchController {

    private Graph graph;

    public SearchController(Graph graph) {
        this.graph = graph;
    }


//    public class Wrapping {
//        Node currentNode;
//        Wrapping previousPath;
//        boolean noPrevPath;
//
//        public Wrapping(Node currentNode, Wrapping previousPath) {
//            this.currentNode = currentNode;
//            this.previousPath = previousPath;
//            noPrevPath = false;
//        }
//
//        public Wrapping(Node currentNode) {
//            this.currentNode = currentNode;
//            noPrevPath = true;
//        }
//
//        public void addPath(Wrapping path) {
//            this.previousPath = path;
//            noPrevPath = false;
//        }
//    }

    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;

    public ArrayList pathOfNodes(int startID, int endID) {


        ArrayList<Integer> queue = new ArrayList<Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();

        int currentID = startID;

        GraphNode currentGNode = currentGNode = graph.getGraphNode(currentID);

        while (currentID != endID) {
            for (int i = 0; i < currentGNode.edgeCount(); i++) {

                Edge currentEdge = currentGNode.getEdge(i);
                int otherNodeID;
                GraphNode otherGNode;

                if (currentEdge.getStartNode() == currentID) { // check whether node is starting node or ending node in the
                    // edge
                    otherNodeID = currentEdge.getEndNode();
                } else {
                    otherNodeID = currentEdge.getStartNode();
                }

//                boolean otherVisited = false;
//                for (int j = 0; j < visited.size(); j++) {
//                    if (otherNode
//                            .getNodeID() == visited.get(j).getNodeID()) { // check if node has been visited
//                        otherVisited = true;
//                    }
//                }

                if (!currentGNode.isVisited()) { // if not visited, add to queue and add to wrapping queue
                    queue.add(otherNodeID);
                }
            }

            currentGNode.setVisited(true);
            currentID = queue.remove(0);
            currentGNode = graph.getGraphNode(currentID);
        }

        while (currentID != startID) { // while the path still has a previous path
            path.add(0, currentID);
            currentGNode = currentGNode.getPrev();
            currentID = currentGNode.getNodeID();
        }
        path.add(0, startID);

        return path;
    }
}
