package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Comparator;

public class List implements Command {

  private final Directory dir;
  private final String order;

  public List(Directory dir, String order) {
    this.dir = dir;
    this.order = order;
  }

  public void execute() {
    ArrayList<String> contents = dir.getFiles();
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
