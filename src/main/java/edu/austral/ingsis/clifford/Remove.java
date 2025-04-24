package edu.austral.ingsis.clifford;

public class Remove implements Command {

  private final String filename;
  private final Directory dir;
  private final boolean recursive;
  
  public Remove(Directory dir, String filename, boolean recursive) {
    this.dir = dir;
    this.filename = filename;
    this.recursive = recursive;
  }
  
  public void execute() {
    if (recursive) {
      dir.removeDir(filename);
    } else {
      dir.removeDoc(filename);
    }
  }
  
}