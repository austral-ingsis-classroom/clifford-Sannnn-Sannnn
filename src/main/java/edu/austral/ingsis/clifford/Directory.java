package edu.austral.ingsis.clifford;

import java.util.ArrayList;

public final class Directory extends File {

  private final Directory parent;
  private final ArrayList<File> contents;

  public Directory(String name, String route, Directory parent) {
    super(name, route);
    this.contents = new ArrayList<>();
    this.parent = parent;
  }

  public Directory getParent() {
    return this.parent;
  }

  public void createDoc(String name) {
    contents.add(new Document(name, getRoute()));
  }

  public ArrayList<String> getFiles() {
    ArrayList<String> output = new ArrayList<>();
    for (File file : contents) {
      output.add(file.getName());
    }
    return output;
  }

}
