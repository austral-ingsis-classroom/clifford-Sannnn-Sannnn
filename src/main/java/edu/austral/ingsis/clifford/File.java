package edu.austral.ingsis.clifford;

public sealed abstract class File permits Document, Directory {

  private final String name;
  private final boolean isDir;
  private final String path;

  public File(String name, String path, boolean isDir) {
    this.name = name;
    this.path = path;
    this.isDir = isDir;
  }

  public String name() {
    return this.name;
  }
  
  public boolean isDirectory() {
    return this.isDir;
  }

  public String path() {
    return this.path;
  }

}
