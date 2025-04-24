package edu.austral.ingsis.clifford;

public class PrintDir implements Command {
  
  private final Directory dir;
  
  public PrintDir(Directory dir) {
    this.dir = dir;
  }
  
  public void execute() {
    System.out.println(dir.getRoute());
  }
  
}
