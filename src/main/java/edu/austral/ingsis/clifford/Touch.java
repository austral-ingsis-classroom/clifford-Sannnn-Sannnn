package edu.austral.ingsis.clifford;

public class Touch implements Command {

  private final Directory dir;
  private final String filename;

  public Touch(Directory dir, String filename) {
    this.dir = dir;
    this.filename = filename;
  }

  public void execute() {
    dir.createDoc(filename);
  }

}
