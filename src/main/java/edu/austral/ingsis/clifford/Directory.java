package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Directory extends File {

  /*
  Having a reference to its parent can make cd easier.
  However, it's not possible to create a truly inmutable structure
   */
  private final List<File> contents;

  public Directory(String name, String path, List<File> contents) {
    super(name, path, true);
    this.contents = Collections.unmodifiableList(new ArrayList<>(contents));
  }

  // Modify logic: Return copy of same Directory with modified copies of contents

  public Directory createDoc(Document doc) {
    List<File> newContents = new ArrayList<>(contents);
    newContents.add(doc);
    return new Directory(name(), path(), newContents);
  }

  public Directory createDir(File dir) {
    List<File> newContents = new ArrayList<>(contents);
    newContents.add(dir);
    return new Directory(name(), path(), newContents);
  }

  public Directory removeDoc(File target) {
    List<File> newContents = new ArrayList<>(contents);
    for (File file : newContents) {
      if (new FileComparator().compare(file, target) == 0) {
        newContents.remove(file);
        return new Directory(name(), path(), newContents);
      }
    }
    return this;
  }

  public Directory removeDir(File target) {
    List<File> newContents = new ArrayList<>(contents);
    for (File file : newContents) {
      if (new FileComparator().compare(file, target) == 0) {
        newContents.remove(file);
        return new Directory(name(), path(), newContents);
      }
    }
    return this;
  }

  public Directory changeDir(Directory oldDir, Directory newDir) {
    List<File> newContents = new ArrayList<>(contents);
    newContents.remove(oldDir);
    newContents.add(newDir);
    return new Directory(name(), path(), newContents);
  }

  public List<File> getFileList() {
    return new ArrayList<>(contents);
  }

  public List<String> getFileNames() {
    List<String> output = new ArrayList<>();
    for (File file : contents) {
      output.add(file.name());
    }
    return output;
  }
}
