package Database;

public class EdgeDAOTest {

    public EdgeDAO setUpEdgeDAO(){
        try {
      DAO<> DBC1 = DatabaseController.INSTANCE;
      return new DAO<>;
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
   return null;
    }

}
