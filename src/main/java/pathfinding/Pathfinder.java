package pathfinding;

import java.util.*;

public class Pathfinder {
    public class Node {
        private List<Node> neighbors;
        private HashMap<Node, Integer> edgeCosts;
        public Node() {
            this.neighbors = new LinkedList<Node>();
            edgeCosts = new HashMap<Node, Integer>();
        }
        public Node addNeighbor(Node neighbor) {
            neighbors.add(neighbor);
            edgeCosts.put(neighbor, 1);
            return this;
        }
        public Node addNeighbor(Node neighbor, int cost) {
            neighbors.add(neighbor);
            edgeCosts.put(neighbor, cost);
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

    /***
     * Finds the least cost path from the starting node to the target node using the A-star algorithm
     * @param from the starting Node
     * @param to the target Node
     * @return a list of coordinates representing the least cost path from the starting node to the target node
     */
    public List<Node> findLeastCostPath(Node from, Node to) {
        HashMap<Node, Integer> costMap = new HashMap<Node, Integer>();
        costMap = calculateFullCostMap(to, from);
        PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.edgeCosts.get(o2) - o2.edgeCosts.get(o1);
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

    private HashMap<Node, Integer> calculateFullCostMap(Node from, Node to) {
        HashMap<Node, Integer> costMap = new HashMap<Node, Integer>();
        LinkedList<Node> queue = new LinkedList<Node>();
        HashSet<Node> visited = new HashSet<Node>();
        queue.add(from);
        costMap.put(from, 0);

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (visited.contains(current)) {
                continue;
            } else {
                visited.add(current);
            }
            for (Node neighbor : current.getNeighbors()) {
                int neighborCost = costMap.get(current) + current.edgeCosts.get(neighbor);
                if (!costMap.containsKey(neighbor) || neighborCost < costMap.get(neighbor)) {
                    queue.add(neighbor);
                    costMap.put(neighbor, costMap.get(current) + current.edgeCosts.get(neighbor));
                }
            }
        }
        return costMap;
    }
}
