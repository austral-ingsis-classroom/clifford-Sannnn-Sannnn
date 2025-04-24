package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Directory extends File {

  private final Directory parent;
  private final List<File> contents;

  public Directory(String name, Directory parent, ArrayList<File> contents) {
    super(name, true);
    this.contents = Collections.unmodifiableList(new ArrayList<>(contents));
    this.parent = parent;
  }

  public Directory getParent() {
    return this.parent;
  }
  
  // Modify logic: Return copy of same Directory with modified copies of contents

  public Directory createDoc(String filename) {
    ArrayList<File> newContents = new ArrayList<>(contents);
    newContents.add(new Document(filename));
    return new Directory(getName(), parent, newContents);
  }

  public Directory createDir(String filename) {
    ArrayList<File> newContents = new ArrayList<>(contents);
    newContents.add(new Directory(filename, parent, new ArrayList<>()));
    return new Directory(getName(), parent, newContents);
  }
  
  public Directory removeDoc(String filename) {
    ArrayList<File> newContents = new ArrayList<>(contents);
    for (File file : newContents) {
      if (file.getName().equals(filename) && !isDirectory()) {
        newContents.remove(file);
        return new Directory(getName(), parent, newContents);
      }
    }
    return this;
  }
  
  public Directory removeDir(String filename) {
    ArrayList<File> newContents = new ArrayList<>(contents);
    for (File file : newContents) {
      if (file.getName().equals(filename) && file.isDirectory()) {
        newContents.remove(file);
        return new Directory(getName(), parent, newContents);
      }
    }
    return this;
  }
  
  public ArrayList<File> getFiles() {
    return new ArrayList<>(contents);
  }

  public ArrayList<String> getFileList() {
    ArrayList<String> output = new ArrayList<>();
    for (File file : contents) {
      output.add(file.getName());
    }
    return output;
  }

}
