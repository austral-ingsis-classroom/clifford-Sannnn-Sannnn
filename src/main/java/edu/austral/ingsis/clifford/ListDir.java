package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Comparator;

public class ListDir implements Command {

  private final Directory dir;
  private final String order;

  public ListDir(Directory dir, String order) {
    this.dir = dir;
    this.order = order;
  }

  public void execute() {
    ArrayList<String> contents = dir.getFileList();
    if (order.equals("asc")) {
      contents.sort(Comparator.naturalOrder());
    } else if (order.equals("desc")) {
      contents.sort(Comparator.reverseOrder());
    }
    for (String file : contents) {
      System.out.println(file);
    }
  }

}
