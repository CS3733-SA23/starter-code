package edu.wpi.teamA.database.DAOImps;

import edu.wpi.teamA.database.Connection.DBConnectionProvider;
import edu.wpi.teamA.database.Interfaces.IFlowerDAO;
import edu.wpi.teamA.database.ORMclasses.FlowerEntity;
import java.sql.*;
import java.util.ArrayList;

public class FlowerDAOImpl implements IFlowerDAO {
  ArrayList<FlowerEntity> flowerArray = new ArrayList<>();
  static DBConnectionProvider flowerProvider = new DBConnectionProvider();

  public FlowerDAOImpl() {
    this.flowerArray = new ArrayList<>();
  }

  public FlowerDAOImpl(ArrayList<FlowerEntity> flowerArray) {
    this.flowerArray = flowerArray;
  }

  @Override
  public void addFlower(FlowerEntity flower) {
    /** Insert new node object to the existing node table */
    try {
      String name = flower.getName();
      int room = flower.getRoom();
      Date date = flower.getDate();
      int time = flower.getTime();
      String type = flower.getFlowerType();
      String comment = flower.getComment();
      String status = flower.getStatus();

      String sqlCreateEdge =
          "Create Table if not exists \"Prototype2_schema\".\"Flower\""
              + "(namee   Varchar(600),"
              + "room    int,"
              + "datee    date,"
              + "timee     int,"
              + "flowerType     Varchar(600),"
              + "comment     Varchar(600),"
              + "status  Varchar(600))";
      Statement stmtFlower = flowerProvider.createConnection().createStatement();
      stmtFlower.execute(sqlCreateEdge);

      PreparedStatement ps =
          flowerProvider
              .createConnection()
              .prepareStatement(
                  "INSERT INTO \"Prototype2_schema\".\"Flower\" VALUES (?, ?, ?, ?, ?, ?, ?)");
      ps.setString(1, name);
      ps.setInt(2, room);
      ps.setDate(3, date);
      ps.setInt(4, time);
      ps.setString(5, type);
      ps.setString(6, comment);
      ps.setString(7, status);
      ps.executeUpdate();

      flowerArray.add(new FlowerEntity(name, room, date, time, type, comment, status));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteFlower(FlowerEntity flower) {

    try {
      PreparedStatement ps =
          flowerProvider
              .createConnection()
              .prepareStatement("DELETE FROM \"Prototype2_schema\".\"Flower\" WHERE name = ?");
      ps.setString(1, flower.getName());
      ps.executeUpdate();

      flowerArray.removeIf(flowerEntity -> flowerEntity.getName().equals(flower.getName()));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void updateFlower(FlowerEntity flower) {}

  @Override
  public void editFlower(FlowerEntity flower) {}
}
