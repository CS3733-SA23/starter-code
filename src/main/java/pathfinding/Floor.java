package pathfinding;

import java.util.NoSuchElementException;

public enum Floor {
  // Use Enum.ordinal() to get int value
  LOWER_TWO,
  LOWER_ONE,
  ONE,
  TWO,
  THREE;

  public static Floor stringToFloor(String floorName) {
    switch (floorName.toUpperCase()) {
      case "L1":
        return LOWER_ONE;
      case "L2":
        return LOWER_TWO;
<<<<<<< HEAD
      case "ONE":
      case "1":
        return ONE;
      case "TWO":
      case "2":
        return TWO;
      case "THREE":
=======
      case "1":
        return ONE;
      case "2":
        return TWO;
>>>>>>> main
      case "3":
        return THREE;
      default:
        throw new NoSuchElementException("No such Floor found");
    }
  }
<<<<<<< HEAD
=======

  public static String floorToString(Floor fl) {
    switch (fl) {
      case LOWER_ONE:
        return "L1";
      case LOWER_TWO:
        return "L2";
      case ONE:
        return "1";
      case TWO:
        return "2";
      case THREE:
        return "3";
      default:
        throw new NoSuchElementException("No such Floor found");
    }
  }
>>>>>>> main
}
