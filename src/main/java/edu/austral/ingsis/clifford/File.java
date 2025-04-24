package edu.austral.ingsis.clifford;

public sealed abstract class File permits Document, Directory {

  private final String name;
  private final String route;

  public File(String name, String route) {
    this.name = name;
    this.route = route;
  }

  public String getName() {
    return this.name;
  }

  public String getRoute() {
    return this.route;
  }

}
