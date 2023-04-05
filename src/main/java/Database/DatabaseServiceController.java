package Database;

import edu.wpi.teame.entities.ServiceRequestData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import org.json.JSONObject;

public class DatabaseServiceController {
  DatabaseController db;

  @Getter List<ServiceRequestData> serviceRequests;

  public DatabaseServiceController(DatabaseController db) {
    try {
      this.db = db;
    } catch (RuntimeException e) {
      throw new RuntimeException("Couldn't connect to the Database\nMake sure you're on WPI wifi");
    }
  }

  public void addServiceRequestToDatabase(ServiceRequestData srd) {
    try {
      Statement stmt = db.getC().createStatement();

      JSONObject requestData = srd.getRequestData();
      String status = ServiceRequestData.Status.statusToString(srd.getRequestStatus());
      String requestType = ServiceRequestData.RequestType.requestTypeToString(srd.getRequestType());

      String sql =
          "INSERT INTO teame.\"ServiceRequests\" "
              + "Values ('"
              + requestData
              + "', '"
              + status
              + "', '"
              + srd.getAssignedStaff()
              + "', '"
              + requestType
              + "');";

      int result = stmt.executeUpdate(sql);

      if (result < 1) {
        System.out.println("There was a problem inserting the table");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public List<ServiceRequestData> retrieveRequestsFromTable() {
    serviceRequests = new LinkedList<>();

    try {
      Statement stmt = db.getC().createStatement();

      String sql = "SELECT * FROM teame.\"ServiceRequests\";";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        ServiceRequestData.RequestType rt =
            ServiceRequestData.RequestType.stringToRequestType(rs.getString("requestType"));

        JSONObject json = new JSONObject(rs.getString("requestdata"));

        ServiceRequestData.Status st =
            ServiceRequestData.Status.stringToStatus(rs.getString("status"));

        String staffassigned = rs.getString("staffassigned");
        serviceRequests.add(new ServiceRequestData(rt, json, st, staffassigned));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return serviceRequests;
  }
}
