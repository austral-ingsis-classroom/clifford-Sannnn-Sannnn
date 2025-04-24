package edu.austral.ingsis.clifford;

public class MakeDir implements Command {
  
  private final String filename;
  private final Directory dir;
  
  public MakeDir(Directory dir, String filename) {
    this.dir = dir;
    this.filename = filename;
  }
  
  public void execute() {
    dir.createDir(filename);
  }
  
}
