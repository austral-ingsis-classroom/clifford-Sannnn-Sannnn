package edu.austral.ingsis.clifford;

public class PrintDir implements Command {

  private final Filesystem filesystem;
  private final Directory dir;
  
  public PrintDir(Filesystem filesystem) {
    this.filesystem = filesystem;
    this.dir = filesystem.getPos();
  }
  
  public InmutableResponse execute() {
    Directory root = filesystem.getRoot();
    String outputMessage = dir.path();
    return new InmutableResponse(root, outputMessage);
  }
  
}
