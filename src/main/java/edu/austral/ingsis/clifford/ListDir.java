package edu.austral.ingsis.clifford;

import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.StringBuilder;

public class ListDir implements Command {

  private final Filesystem filesystem;
  private final Directory dir;
  private final String order;

  public ListDir(Filesystem filesystem, String order) {
    this.filesystem = filesystem;
    this.dir = filesystem.getPos();
    this.order = order;
  }

  public InmutableResponse execute() {
    Directory root = filesystem.getRoot();
    String outputMessage = listToString(sortContent());
    return new InmutableResponse(root, outputMessage);
  }

  private ArrayList<String> sortContent() {
    ArrayList<String> contents = dir.getFileNames();
    if (order.equals("asc")) {
      contents.sort(Comparator.naturalOrder());
    } else if (order.equals("desc")) {
      contents.sort(Comparator.reverseOrder());
    }
    return contents;
  }

  private String listToString(ArrayList<String> contents) {
    StringBuilder output = new StringBuilder();
    for (String file : contents) {
      output.append(file);
      output.append(" ");
    }
    return output.toString().strip(); // Delete last " "
  }

}
