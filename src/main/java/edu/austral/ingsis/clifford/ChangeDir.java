package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;

public class ChangeDir implements Command {

  private final Filesystem filesystem;
  private final Directory dir;
  private final String path;
  private final boolean fromRoot;

  public ChangeDir(Filesystem filesystem, String path) {
    this.filesystem = filesystem;
    this.dir = filesystem.getPos();
    this.path = path;
    this.fromRoot = path.charAt(0) == '/';
  }

  /*
  @Override
  public InmutableResponse execute() {
    List<String> cdPath;
    if (fromRoot) { // Start from root
       cdPath = new ArrayList<>();
    } else { // Start from current position
      cdPath = new ArrayList<>(List.of(dir.path().split("/")));
      cdPath.removeIf(String::isEmpty); // Create ArrayList cleaning all possible ""
    }
    ArrayList<String> cleanParts = new ArrayList<>(List.of(path.split("/"))); // Take desired path
    cleanParts.removeIf(String::isEmpty); // clean all possible ""
    for (String dir : cleanParts) {
      switch (dir) {
        case ".":
            break;
        case "..":
          if (!cdPath.isEmpty()) { // Check that path can go back
            cdPath.removeLast();
          } // else -> attempting to go to root's parent -> stay in root
          break;
        default:
          cdPath.add(dir);
      }
    }
    return filesystem.changeDir(cdPath);
  }
   */

  @Override
  public InmutableResponse execute() {
    List<String> cdPath = initializePath();
    List<String> pathClean = splitAndClean(path);
    updatePath(cdPath, pathClean);
    return filesystem.changeDir(cdPath);
  }

  private List<String> initializePath() {
    if (fromRoot) { // Start from root
      return new ArrayList<>();
    } // else start from current position
    return splitAndClean(dir.path());
  }

  private List<String> splitAndClean(String path) {
    List<String> parts = new ArrayList<>(List.of(path.split("/")));
    parts.removeIf(String::isEmpty); // Remove all possible ""
    return parts;
  }

  private void updatePath(List<String> cdPath, List<String> desiredPath) {
    for (String dir : desiredPath) {
      switch (dir) {
        case "." -> {
          /* do nothing */
        }
        case ".." -> {
          if (!cdPath.isEmpty()) cdPath.removeLast();
        } // Go back or stay if in root
        default -> cdPath.add(dir);
      }
    }
  }
}
