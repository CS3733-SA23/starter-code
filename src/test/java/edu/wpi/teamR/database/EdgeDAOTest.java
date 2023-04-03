package edu.wpi.teamR.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDAOTest {

    static NodeDAO nodeDAO;
    static EdgeDAO edgeDAO;

    @BeforeAll
    static void setUp() throws Exception {
        String username = "teamr";
        String password = "teamr150";
        String schemaName = "test";
        String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb";

        nodeDAO = NodeDAO.createInstance(username, password, "node", schemaName, url);
        edgeDAO = EdgeDAO.createInstance(username, password, "edge", schemaName, url);
        nodeDAO.addNode(1, 0, 0, "", "");
        nodeDAO.addNode(2, 1, 1, "", "");
        nodeDAO.addNode(3, 2, 0, "", "");
        nodeDAO.addNode(4, 1, -1, "", "");
        nodeDAO.addNode(5, 1, 0, "", "");
        nodeDAO.addNode(6, 3, 0, "", "");
        edgeDAO.addEdge(1, 2);
        edgeDAO.addEdge(1, 4);
        edgeDAO.addEdge(1, 5);
        edgeDAO.addEdge(2, 5);
        edgeDAO.addEdge(5, 4);
        edgeDAO.addEdge(2, 3);
        edgeDAO.addEdge(5, 3);
        edgeDAO.addEdge(4, 3);
        edgeDAO.addEdge(3, 6);
    }

    @AfterAll
    static void tearDown() throws Exception {
        nodeDAO.deleteNodes(null, null, null, null, null);
    }

    @Test
    void getEdges() {
        assertEquals(edgeDAO.getEdges().size(), 9);
    }

    @Test
    void addEdgeAndDeleteConnectingEdge() throws SQLException, ClassNotFoundException {
        edgeDAO.addEdge(2, 6);
        edgeDAO.addEdge(4, 6);
        edgeDAO.deleteConnectingEdge(2, 6);
        edgeDAO.deleteConnectingEdge(4, 6);

        edgeDAO.addEdge(6, 2);
        edgeDAO.addEdge(6, 4);
        edgeDAO.deleteConnectingEdge(2, 6);
        edgeDAO.deleteConnectingEdge(4, 6);
    }

    @Test
    void getAdjacentNodeIDs() {
        ArrayList<Integer> nodes;
        nodes = edgeDAO.getAdjacentNodeIDs(6);
        assertEquals(nodes.size(), 1);
        assertEquals(nodes.get(0), 3);

        nodes = edgeDAO.getAdjacentNodeIDs(3);
        assertEquals(nodes.size(), 4);
        assertTrue(nodes.contains(6));
        assertTrue(nodes.contains(2));
        assertTrue(nodes.contains(5));
        assertTrue(nodes.contains(4));

        nodes = edgeDAO.getAdjacentNodeIDs(5);
        assertEquals(nodes.size(), 4);
        assertTrue(nodes.contains(2));
        assertTrue(nodes.contains(1));
        assertTrue(nodes.contains(4));
        assertTrue(nodes.contains(3));
    }

    @Test
    void deleteAllEdges() throws SQLException, ClassNotFoundException {
        ArrayList<Integer> nodes;

        edgeDAO.deleteAllEdges(5);
        nodes = edgeDAO.getAdjacentNodeIDs(1);
        assertEquals(nodes.size(), 2);
        assertTrue(nodes.contains(2));
        assertTrue(nodes.contains(4));

        edgeDAO.deleteAllEdges(3);
        nodes = edgeDAO.getAdjacentNodeIDs(6);
        assertEquals(nodes.size(), 0);

        nodeDAO.deleteNodes(1, null, null, null, null);
        nodes = edgeDAO.getAdjacentNodeIDs(4);
        assertEquals(nodes.size(), 0);
    }
}