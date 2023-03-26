package pathfinding;

import java.util.*;

public class Pathfinder {
    public class Node {
        private List<Node> neighbors;
        private HashMap<Node, Integer> costMap;
        public Node() {
            this.neighbors = new LinkedList<Node>();
            costMap = new HashMap<Node, Integer>();
        }
        public Node addNeighbor(Node neighbor) {
            neighbors.add(neighbor);
            costMap.put(neighbor, 1);
            return this;
        }
        public Node addNeighbor(Node neighbor, int cost) {
            neighbors.add(neighbor);
            costMap.put(neighbor, cost);
            return this;
        }
        public List<Node> getNeighbors() {
            return neighbors;
        }
        public static void addEdge(Node node1, Node node2) {
            node1.neighbors.add(node2);
            node2.neighbors.add(node1);
        }
    }

    /***
     * Finds the shortest path from the starting node to the target node using Breadth First Search
     * @param from the starting Node
     * @param to the target Node
     * @return a list of coordinates representing the shortest path from the starting node to the target node
     */
    public List<Node> findShortestPath(Node from, Node to){
        LinkedList<Node> queue = new LinkedList<Node>();
        HashSet<Node> visited = new HashSet<Node>();
        HashMap<Node, Node> parentMap = new HashMap<Node, Node>();
        queue.add(from);
        parentMap.put(from, null);

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (visited.contains(current)) {
                continue;
            } else {
                visited.add(current);
            }
            if (current.equals(to)) {
                return reconstructPath(parentMap, current);
            }
            for (Node neighbor : current.getNeighbors()) {
                if (!parentMap.containsKey(neighbor)) {
                    queue.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        return null;
    }

    /***
     * Reconstructs the path from the starting coordinate to the target coordinate
     * @param parentMap a map of coordinates to their parent coordinates
     * @param current the target coordinate
     * @return a list of coordinates representing the shortest path from the starting coordinate to the target coordinate
     */
    private List<Node> reconstructPath(HashMap<Node, Node> parentMap, Node current){
        LinkedList<Node> path = new LinkedList<Node>();
        while(current != null){
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }

    public List<Node> findLeastCostPath(Node from, Node to) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.costMap.get(o2) - o2.costMap.get(o1);
            }
        });
        HashSet<Node> visited = new HashSet<Node>();
        HashMap<Node, Node> parentMap = new HashMap<Node, Node>();
        queue.add(from);
        parentMap.put(from, null);

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (visited.contains(current)) {
                continue;
            } else {
                visited.add(current);
            }
            if (current.equals(to)) {
                return reconstructPath(parentMap, current);
            }
            for (Node neighbor : current.getNeighbors()) {
                if (!parentMap.containsKey(neighbor)) {
                    queue.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        return null;
    }
}
