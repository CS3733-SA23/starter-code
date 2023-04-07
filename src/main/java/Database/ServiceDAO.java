package Database;

import edu.wpi.teame.entities.ServiceRequestData;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ServiceDAO<E> extends DAO<ServiceRequestData> {
  List<ServiceRequestData> serviceRequestDataList;

  @Override
  public List<ServiceRequestData> get() {
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
  public void update() {}

  @Override
  public void delete(ServiceRequestData obj) {
    try {
      Statement stmt = activeConnection.createStatement();
      int deletionHash = obj.getRequestData().hashCode();

      String sql = "DELETE FROM \"ServiceRequests\" WHERE \"HashID\" = " + deletionHash + ";";

      int result = stmt.executeUpdate(sql);

      if (result < 1) System.out.println("There was a problem deleting the ServiceRequest");
    } catch (SQLException e) {
      throw new RuntimeException("There was a problem deleting the ServiceRequest");
    }
  }

  @Override
  public void add(ServiceRequestData obj) {
    try {
      Statement stmt = activeConnection.createStatement();

      JSONObject requestData = obj.getRequestData();
      String status = ServiceRequestData.Status.statusToString(obj.getRequestStatus());
      String requestType = ServiceRequestData.RequestType.requestTypeToString(obj.getRequestType());
      int hashID = requestData.hashCode();

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
  public void importFromCSV(String filePath, String tableName) {}
}
