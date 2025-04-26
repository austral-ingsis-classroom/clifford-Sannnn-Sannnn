package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.lang.StringBuilder;

public class ListDir implements Command {

  private final Directory dir;
  private final String order;

  public ListDir(Filesystem filesystem, String[] arguments) {
    this.dir = filesystem.getPos();
    if (arguments.length > 1) {
      this.order = arguments[1];
    } else {
      this.order = "";
    }
  }

  public InmutableResponse execute() {
    String outputMessage = listToString(sortContent());
    return new InmutableResponse(dir, outputMessage);
  }

  private List<String> sortContent() {
    List<String> contents = dir.getFileNames();
    if (order.equals("--ord=asc")) {
      contents.sort(Comparator.naturalOrder());
    } else if (order.equals("--ord=desc")) {
      contents.sort(Comparator.reverseOrder());
    }
    return contents;
  }

  private String listToString(List<String> contents) {
    StringBuilder output = new StringBuilder();
    for (String file : contents) {
      output.append(file);
      output.append(" ");
    }
    return output.toString().strip(); // Delete last " "
  }

}
