package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;

public class Filesystem {

  private Directory root;
  private Directory position;

  public Filesystem(Directory root) {
    this.root = root;
    this.position = root;
  }

  public Filesystem() {
    this.root = new Directory("/", "", new ArrayList<>());
    this.position = this.root;
  }

  // mkdir

  public InmutableResponse addDirectory(String path, String filename) {
    List<String> appendPath = new ArrayList<>(List.of(path.split("/")));
    appendPath.removeFirst(); // Remove first element -> empty string at the left of "/"
    InmutableResponse response = addDirectory(root, appendPath, filename);
    root = response.dir();
    updatePosition();
    return response;
  }

  /*
   if path is empty => found directory:
     check there's no directory with the same name
       return new directory with name
     else return file with same name
   else if current.name == path.getFirst => right directory in path:
     search for directory in contents with next name in path
       return addDirectory()
     else
       print error
       return current
   else => directory not found:
     Print error
     return current
  */

  private InmutableResponse addDirectory(Directory current, List<String> path, String filename) {
    if (path.isEmpty()) {
      return createDirectory(current, filename); // Create Directory on current
    } // else continue searching
    InmutableResponse response = continueDirPath(current, path, filename);
    return new InmutableResponse(response.dir(), response.message());
  }

  private InmutableResponse createDirectory(Directory current, String filename) {
    for (File file :
        current.getFileList()) { // Check if there already is a directory with the same name
      if (file.isDirectory() && file.name().equals(filename)) {
        String outputMessage = "'" + filename + "' already exists";
        return new InmutableResponse(current, outputMessage); // returns current with no changes
      }
    } // Directory does not exist yet
    String outputMessage = "'" + filename + "' directory created";
    Directory newDir =
        current.createDir(
            new Directory(filename, current.path() + "/" + filename, new ArrayList<>()));
    return new InmutableResponse(newDir, outputMessage);
  }

  private InmutableResponse continueDirPath(Directory current, List<String> path, String filename) {
    for (File file : current.getFileList()) { // Check next directory exists
      if (file.isDirectory() && file.name().equals(path.getFirst())) {
        path.removeFirst();
        InmutableResponse response = addDirectory((Directory) file, path, filename);
        Directory newDir = current.changeDir((Directory) file, response.dir());
        return new InmutableResponse(newDir, response.message());
      }
    } // Directory from path not found -> Stop
    String outputMessage = "'" + path.getFirst() + "' directory not found";
    return new InmutableResponse(current, outputMessage);
  }

  // touch

  public InmutableResponse addDocument(String path, String filename) {
    List<String> appendPath = new ArrayList<>(List.of(path.split("/")));
    appendPath.removeFirst();
    InmutableResponse response = addDocument(root, appendPath, filename);
    root = response.dir();
    updatePosition();
    return response;
  }

  private InmutableResponse addDocument(Directory current, List<String> path, String filename) {
    if (path.isEmpty()) {
      return createDocument(current, filename); // Create Document on current
    } // else continue searching
    InmutableResponse response = continueDocPath(current, path, filename);
    return new InmutableResponse(response.dir(), response.message());
  }

  private InmutableResponse createDocument(Directory current, String filename) {
    for (File file :
        current.getFileList()) { // Check if there already is a document with the same name
      if (!file.isDirectory() && file.name().equals(filename)) {
        String outputMessage = "'" + filename + "' already exists";
        return new InmutableResponse(current, outputMessage); // returns current with no changes
      }
    } // Document does not exist yet
    String outputMessage = "'" + filename + "' file created";
    Directory newDir = current.createDoc(new Document(filename, current.path() + "/" + filename));
    return new InmutableResponse(newDir, outputMessage);
  }

  private InmutableResponse continueDocPath(Directory current, List<String> path, String filename) {
    for (File file : current.getFileList()) { // Check next directory exists
      if (file.isDirectory() && file.name().equals(path.getFirst())) {
        path.removeFirst();
        InmutableResponse response = addDocument((Directory) file, path, filename);
        Directory newDir = current.changeDir((Directory) file, response.dir());
        return new InmutableResponse(newDir, response.message());
      }
    } // Directory from path not found -> Stop
    String outputMessage = "'" + path.getFirst() + "' directory not found";
    return new InmutableResponse(current, outputMessage);
  }

  // remove

  public InmutableResponse removeFile(String path, String filename, boolean recursive) {
    List<String> appendPath = new ArrayList<>(List.of(path.split("/")));
    appendPath.removeFirst();
    InmutableResponse response = removeFile(root, appendPath, filename, recursive);
    root = response.dir();
    updatePosition();
    return response;
  }

  private InmutableResponse removeFile(
      Directory current, List<String> path, String filename, boolean recursive) {
    if (path.isEmpty() && recursive) {
      return deleteDir(current, filename); // Remove file on current
    } else if (path.isEmpty()) {
      return deleteDoc(current, filename); // Remove document on file
    } // else continue searching
    InmutableResponse response = continueRemovePath(current, path, filename, recursive);
    return new InmutableResponse(response.dir(), response.message());
  }

  private InmutableResponse deleteDir(
      Directory current, String filename) { // can remove directories and documents
    File target = findFile(current, filename);
    if (target == null) { // Document does not exist
      String outputMessage = "'" + filename + "' not found";
      return new InmutableResponse(current, outputMessage);
    } else {
      String outputMessage = "'" + filename + "' removed";
      Directory newDir = current.removeDir(target);
      return new InmutableResponse(newDir, outputMessage);
    }
  }

  private InmutableResponse deleteDoc(
      Directory current, String filename) { // Only removes documents
    File target = findFile(current, filename);
    if (target == null) { // Document does not exist
      String outputMessage = "'" + filename + "' not found";
      return new InmutableResponse(current, outputMessage);
    }
    return attemptDeleteDocument(current, target, filename);
  }

  private InmutableResponse attemptDeleteDocument(Directory current, File target, String filename) {
    if (!target.isDirectory()) { // Delete document
      String outputMessage = "'" + filename + "' removed";
      Directory newDir = current.removeDoc(target);
      return new InmutableResponse(newDir, outputMessage);
    } else { // It's a directory -> cannot delete
      String outputMessage = "cannot remove '" + filename + "', is a directory";
      return new InmutableResponse(current, outputMessage);
    }
  }

  private File findFile(Directory current, String filename) {
    File output = null;
    for (File file : current.getFileList()) { // Check if there is a file with the specified name
      if (!file.isDirectory() && file.name().equals(filename)) { // Found possible target file
        output = file;
      } else if (file.isDirectory() && file.name().equals(filename)) {
        return file;
      }
    }
    return output;
  }

  private InmutableResponse continueRemovePath(
      Directory current, List<String> path, String filename, boolean recursive) {
    for (File file : current.getFileList()) { // Check next directory exists
      if (file.isDirectory() && file.name().equals(path.getFirst())) {
        path.removeFirst();
        InmutableResponse response = removeFile((Directory) file, path, filename, recursive);
        Directory newDir = current.changeDir((Directory) file, response.dir());
        return new InmutableResponse(newDir, response.message());
      }
    } // Directory from path not found -> Stop
    String outputMessage = "'" + path.getFirst() + "' directory not found";
    return new InmutableResponse(current, outputMessage);
  }

  // Change Directory

  /*
  public InmutableResponse changeDir(List<String> path) {
    position = root;
    for (String directory : path) {
      boolean found = false;
      for (File file : position.getFileList()) { // Search for next directory
        if (file.isDirectory() && file.name().equals(directory)) {
          position = (Directory) file;
          found = true;
          break;
        }
      }
      if (!found) {
        return new InmutableResponse(position, "'" + directory + "' directory does not exist");
      }
    }
    return new InmutableResponse(position, "moved to directory '" + position.name() + "'");
  }
  */

  public InmutableResponse changeDir(List<String> path) {
    position = root;
    for (String directory : path) { // Recursively search for next directory
      InmutableResponse response = moveToDirectory(directory);
      if (!response.message().startsWith("moved")) { // Next directory not found
        return response;
      }
    } // Reached end of path
    String outputMessage = "moved to directory '" + position.name() + "'";
    return new InmutableResponse(position, outputMessage);
  }

  private InmutableResponse moveToDirectory(String directory) {
    for (File file : position.getFileList()) {
      if (isMatchingDirectory(file, directory)) { // Found
        position = (Directory) file;
        return new InmutableResponse(position, "moved");
      }
    } // Not found
    String outputMessage = "'" + directory + "' directory does not exist";
    return new InmutableResponse(position, outputMessage);
  }

  private boolean isMatchingDirectory(File file, String directory) {
    return file.isDirectory() && file.name().equals(directory);
  }

  private void updatePosition() {
    List<String> newPath = List.of(position.path().split("/"));
    position = root;
    for (String directory : newPath) {
      for (File file : position.getFileList()) { // Search for next directory
        if (file.isDirectory() && file.name().equals(directory)) {
          position = (Directory) file;
          break;
        }
      }
    }
  }

  // Misc

  public Directory getRoot() {
    return this.root;
  }

  public Directory getPos() {
    return this.position;
  }
}
