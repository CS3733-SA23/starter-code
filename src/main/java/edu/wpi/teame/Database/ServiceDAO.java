package edu.wpi.teame.Database;

import edu.wpi.teame.entities.ServiceRequestData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

public class ServiceDAO<E> extends DAO<ServiceRequestData> {
  List<ServiceRequestData> serviceRequestDataList;

  public ServiceDAO(Connection c) {
    activeConnection = c;
    table = "\"ServiceRequests\"";
  }

  @Override
  List<ServiceRequestData> get() {
    serviceRequestDataList = new LinkedList<>();

    try {
      Statement stmt = activeConnection.createStatement();

      String sql = "SELECT * FROM \"ServiceRequests\";";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        ServiceRequestData.RequestType rt =
            ServiceRequestData.RequestType.stringToRequestType(rs.getString("requestType"));

        JSONObject json = new JSONObject(rs.getString("requestdata"));

        ServiceRequestData.Status st =
            ServiceRequestData.Status.stringToStatus(rs.getString("status"));

        String staffassigned = rs.getString("staffassigned");
        serviceRequestDataList.add(new ServiceRequestData(rt, json, st, staffassigned));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return serviceRequestDataList;
  }

  @Override
  void update(ServiceRequestData obj, String attribute, String value) {
    int hashID = obj.getRequestData().toString().hashCode();
    String sql = "";

    try {
      Statement stmt = activeConnection.createStatement();

      if (attribute.equals("requestdata")) {
        JSONObject json = new JSONObject(value);
        sql =
            "UPDATE \"ServiceRequests\" "
                + "SET \""
                + attribute
                + "\" = '"
                + value
                + "', \"HashID\" = "
                + value.hashCode()
                + " WHERE \"HashID\" = "
                + hashID
                + ";";
      } else {
        sql =
            "UPDATE \"ServiceRequests\" "
                + "SET \""
                + attribute
                + "\" = '"
                + value
                + "' WHERE \"HashID\" = "
                + hashID
                + ";";
      }
      int result = stmt.executeUpdate(sql);
      if (result < 1) System.out.println("There was a problem updating that ServiceRequest");
    } catch (SQLException e) {
      throw new RuntimeException("There was a problem updating that ServiceRequest");
    }
  }

  @Override
  void delete(ServiceRequestData obj) {
    try {
      Statement stmt = activeConnection.createStatement();
      int deletionHash = obj.getRequestData().toString().hashCode();

      String sql = "DELETE FROM \"ServiceRequests\" WHERE \"HashID\" = " + deletionHash + ";";

      int result = stmt.executeUpdate(sql);

      if (result < 1) System.out.println("There was a problem deleting the ServiceRequest");
    } catch (SQLException e) {
      throw new RuntimeException("There was a problem deleting the ServiceRequest");
    }
  }

  @Override
  void add(ServiceRequestData obj) {
    try {
      Statement stmt = activeConnection.createStatement();

      JSONObject requestData = obj.getRequestData();
      String status = ServiceRequestData.Status.statusToString(obj.getRequestStatus());
      String requestType = ServiceRequestData.RequestType.requestTypeToString(obj.getRequestType());
      int hashID = requestData.toString().hashCode();

      String sql =
          "INSERT INTO teame.\"ServiceRequests\" "
              + "Values ('"
              + requestData
              + "', '"
              + status
              + "', '"
              + obj.getAssignedStaff()
              + "', '"
              + requestType
              + "', "
              + hashID
              + ");";

      int result = stmt.executeUpdate(sql);

      if (result < 1) {
        System.out.println("There was a problem inserting the table");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  void importFromCSV(String filePath, String tableName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line;
      List<String> rows = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        rows.add(line);
      }
      rows.remove(0);
      reader.close();

      String sqlDelete = "DELETE FROM \"" + tableName + "\";";
      Statement stmt = activeConnection.createStatement();
      stmt.execute(sqlDelete);

      for (String l1 : rows) {
        int lastCurly = l1.lastIndexOf("}");
        JSONObject Json = new JSONObject(l1.substring(0, lastCurly + 1));
        String notJson = l1.substring(lastCurly + 2);

        String[] splitL1 = notJson.split(",");
        String sql =
            "INSERT INTO \""
                + tableName
                + "\" "
                + "VALUES ('"
                + Json
                + "', '"
                + splitL1[0]
                + "', '"
                + splitL1[1]
                + "', '"
                + splitL1[2]
                + "', "
                + Json.toString().hashCode()
                + "); ";
        try {
          stmt.execute(sql);
        } catch (SQLException e) {
          System.out.println("Could not import " + Json);
        }
      }
    } catch (IOException e) {
      System.err.println("Error importing from " + filePath + " to " + tableName);
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
