package edu.wpi.teamc.MapDisplay;

public class Table_Edit {
  private String LongName;
  private String ShortName;
  private String NodeType;

  public Table_Edit() {
    this.LongName = "";
    this.ShortName = "";
    this.NodeType = "";
  }

  public Table_Edit(String longName, String shortName, String nodeType) {
    this.LongName = longName;
    this.ShortName = shortName;
    this.NodeType = nodeType;
  }

  public String getNodeType() {
    return NodeType;
  }

  public void setNodeType(String nodeType) {
    this.NodeType = nodeType;
  }

  public String getLongName() {
    return LongName;
  }

  public void setLongName(String longName) {
    this.LongName = longName;
  }

  public String getShortName() {
    return ShortName;
  }

  public void setShortName(String shortname) {
    this.ShortName = shortname;
  }
}
