package edu.wpi.teamR.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveDAOTest {
    static MoveDAO dao;

    @BeforeAll
    static void setUp() throws Exception {
        dao = MoveDAO.createInstance("teamr", "teamr150", "node",
                "test", "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        dao.addMove(342, "The Move #1",8989); //TODO: Fix moveDate

    }

    @AfterAll
    static void tearDown() throws Exception {
        dao.deleteMove(null, null, null);
    }

    @Test
    void createInstance() {
        MoveDAO dao2 = MoveDAO.createInstance("teamr", "teamr150", "node",
                "test", "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        assertSame(dao, dao2);
    }

    @Test
    void getInstance() {
        MoveDAO dao2 = MoveDAO.getInstance();
        assertSame(dao, dao2);
    }

    @Test
    void addAndDeleteMove() throws Exception {
        Move move = new Move(343, "The Move #2", 11/11/11); //TODO: Fix moveDate
        dao.addMove(move.getNodeID(),move.getLongName(),move.getMoveDate());
        Move move2 = dao.selectMoves(343, null, null).get(0);
        boolean sameID = move.getNodeID()==move2.getNodeID();
        boolean sameLongName = move.getLongName()==move2.getLongName();
        boolean sameMoveDate = move.getMoveDate()==move2.getMoveDate();
        assertTrue(sameID && sameLongName && sameMoveDate);
        dao.deleteMove(343, null, null);
        assertEquals(dao.selectMoves(343, null, null).size(), 0);
    }

    @Test
    void modifyMoveByID() throws SQLException, ClassNotFoundException, NotFoundException {
        dao.addMove(344, "The Move #3", 100); //TODO: Fix moveDate
        dao.modifyMoveByID(344, "The Move #4", 0); //TODO: Fix moveDate
        ArrayList<Move> moves = dao.selectMoves(null, null, null);
        assertEquals(moves.size(), 1);
        Move move = moves.get(0);
        Move move2 = new Move(344, "The Move #5", 0); //TODO: Fix moveDate
        boolean sameID = move.getNodeID()==move2.getNodeID();
        boolean sameLongName = move.getLongName()==move2.getLongName();
        boolean sameMoveDate = move.getMoveDate()==move2.getMoveDate();
        assertTrue(sameID && sameLongName && sameMoveDate);
        dao.deleteMove(344, null, null);
    }

    @Test
    void selectMoves() throws SQLException, ClassNotFoundException {
        dao.addMove(1, "The Move #6", 0); //TODO: Fix moveDate
        dao.addMove(2, "The Move #7", 0); //TODO: Fix moveDate
        dao.addMove(3, "The Move #8", 0); //TODO: Fix moveDate
        dao.addMove(4, "The Move #9", 1); //TODO: Fix moveDate

        ArrayList<Move> moves = dao.selectMoves(null, null, null);
        assertEquals(moves.size(), 5);

        moves = dao.selectMoves(null, "The Move #10", 0); //TODO: Fix moveDate
        assertEquals(moves.size(), 1);

        moves = dao.selectMoves(2, "The Move #11", 0); //TODO: Fix moveDate
        assertEquals(moves.size(), 0);

        moves = dao.selectMoves(2, "The Move #12", 0); //TODO: Fix moveDate
        assertEquals(moves.size(), 0);

        moves = dao.selectMoves(3, null, null); //TODO: Fix moveDate
        assertEquals(moves.size(), 1);
    }

    /*
    @Test
    void writeCSV() {

    }

    @Test
    void readCSV() {
    }
     */
}
