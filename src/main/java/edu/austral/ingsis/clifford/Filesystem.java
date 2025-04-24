package edu.austral.ingsis.clifford;

import java.util.ArrayList;

public class Filesystem {
  
  Directory root;
  Directory position;
  
  public Filesystem(Directory root) {
    this.root = root;
  }
  
  /*
  IDEA: reconstruct the filesystem from the current position with Directory parent parameter
    * Use current to store it on Filesystem
    * Make a function to search for the root (parent until it has ni parent) and store it in root
   */
  
  public Filesystem addDirectory(String path, String filename) {
    Directory newRoot = addDirectory(root, , filename);
    return new Filesystem(newRoot);
  }
  
  private Directory addDirectory(Directory current, ArrayList<String> path, String filename) {
    if (path.isEmpty()) {
      return current.createDir(filename);
    }
    ArrayList<File> updatedContent = new ArrayList<>();
    boolean found = false;
    
    for (File file : current.getFiles()) {
      if (file.isDirectory() && file.getName().equals(path.getFirst())) {
        Directory updated = addDirectory((Directory) file, path, filename);
        updatedContent.add(updated);
        found = true;
      } else {
        updatedContent.add(file);
      }
    }
    
    if (!found) {
      Directory newDir = addDirectory(new Directory(, new ArrayList<>()), path, filename);
      updatedContent.add(newDir);
    }
    
    return new Directory(current.getName(), updatedContent);
  }
  
  public void takeCommand(String command) {
  
  }
  
}
