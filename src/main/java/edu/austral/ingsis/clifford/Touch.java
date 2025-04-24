package edu.austral.ingsis.clifford;

public class Touch implements Command {

  private final Directory dir;
  private final String name;

  public Touch(Directory dir, String name) {
    this.dir = dir;
    this.name = name;
  }

  public void execute() {
    dir.createDoc(name);
  }

}
