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
    this.root = new Directory("root", "/", new ArrayList<>());
    this.position = this.root;
  }

  // mkdir

  public InmutableResponse addDirectory(String path, String filename) {
    List<String> appendPath = new ArrayList<>(List.of(path.split("/")));
    InmutableResponse response = addDirectory(root, appendPath, filename);
    root = response.dir();
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
    for (File file : current.getFileList()) { // Check if there already is a directory with the same name
      if (file.isDirectory() && file.name().equals(filename)) {
        String outputMessage = "'" + filename + "' already exists";
        return new InmutableResponse(current, outputMessage); // returns current with no changes
      }
    } // Directory does not exist yet
    String outputMessage = "'" + filename + "' directory created";
    Directory newDir = current.createDir(
        new Directory(filename, current.path() + "/" + current.name(), new ArrayList<>())
    );
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
    InmutableResponse response = addDocument(root, appendPath, filename);
    root = response.dir();
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
    for (File file : current.getFileList()) { // Check if there already is a document with the same name
      if (!file.isDirectory() && file.name().equals(filename)) {
        String outputMessage = "'" + filename + "' already exists";
        return new InmutableResponse(current, outputMessage); // returns current with no changes
      }
    } // Document does not exist yet
    String outputMessage = "'" + filename + "' file created";
    Directory newDir = current.createDoc(
        new Document(filename, current.path()));
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

  // Directory

  public InmutableResponse removeDir(String path, String filename) {
    List<String> appendPath = new ArrayList<>(List.of(path.split("/")));
    InmutableResponse response = removeDir(root, appendPath, filename);
    root = response.dir();
    return response;
  }

  private InmutableResponse removeDir(Directory current, List<String> path, String filename) {
    if (path.isEmpty()) {
      return deleteDir(current, filename); // Create Document on current
    } // else continue searching
    InmutableResponse response = continueDeleteDirPath(current, path, filename);
    return new InmutableResponse(response.dir(), response.message());
  }

  private InmutableResponse deleteDir(Directory current, String filename) {
    for (File file : current.getFileList()) { // Check if there is a directory with the specified name
      if (file.isDirectory() && file.name().equals(filename)) {
        String outputMessage = "'" + filename + "' removed";
        Directory newDir = current.removeDir(filename);
        return new InmutableResponse(newDir, outputMessage);
      }
    } // Document does not exist
    String outputMessage = "'" + filename + "' not found";
    return new InmutableResponse(current, outputMessage);
  }

  private InmutableResponse continueDeleteDirPath(Directory current, List<String> path, String filename) {
    for (File file : current.getFileList()) { // Check next directory exists
      if (file.isDirectory() && file.name().equals(path.getFirst())) {
        path.removeFirst();
        InmutableResponse response = removeDir((Directory) file, path, filename);
        Directory newDir = current.changeDir((Directory) file, response.dir());
        return new InmutableResponse(newDir, response.message());
      }
    } // Directory from path not found -> Stop
    String outputMessage = "'" + path.getFirst() + "' directory not found";
    return new InmutableResponse(current, outputMessage);
  }

  // Document

  public InmutableResponse removeDoc(String path, String filename) {
    List<String> appendPath = new ArrayList<>(List.of(path.split("/")));
    InmutableResponse response = removeDoc(root, appendPath, filename);
    root = response.dir();
    return response;
  }

  private InmutableResponse removeDoc(Directory current, List<String> path, String filename) {
    if (path.isEmpty()) {
      return deleteDoc(current, filename); // Create Document on current
    } // else continue searching
    InmutableResponse response = continueDeleteDocPath(current, path, filename);
    return new InmutableResponse(response.dir(), response.message());
  }

  private InmutableResponse deleteDoc(Directory current, String filename) {
    for (File file : current.getFileList()) { // Check if there is a document with the specified name
      if (!file.isDirectory() && file.name().equals(filename)) {
        String outputMessage = "'" + filename + "' removed";
        Directory newDir = current.removeDoc(filename);
        return new InmutableResponse(newDir, outputMessage);
      }
    } // Document does not exist
    String outputMessage = "'" + filename + "' not found";
    return new InmutableResponse(current, outputMessage);
  }

  private InmutableResponse continueDeleteDocPath(Directory current, List<String> path, String filename) {
    for (File file : current.getFileList()) { // Check next directory exists
      if (file.isDirectory() && file.name().equals(path.getFirst())) {
        path.removeFirst();
        InmutableResponse response = removeDoc((Directory) file, path, filename);
        Directory newDir = current.changeDir((Directory) file, response.dir());
        return new InmutableResponse(newDir, response.message());
      }
    } // Directory from path not found -> Stop
    String outputMessage = "'" + path.getFirst() + "' directory not found";
    return new InmutableResponse(current, outputMessage);
  }

  // Misc

  public Directory getRoot() {
    return this.root;
  }

  public Directory getPos() {
    return this.position;
  }

}
