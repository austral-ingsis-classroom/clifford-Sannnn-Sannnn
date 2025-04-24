package edu.austral.ingsis.clifford;

public sealed abstract class File permits Document, Directory {

  private final String name;
  private final boolean isDir;

  public File(String name, boolean isDir) {
    this.name = name;
    this.isDir = isDir;
  }

  public String getName() {
    return this.name;
  }
  
  public boolean isDirectory() {
    return this.isDir;
  }

}
