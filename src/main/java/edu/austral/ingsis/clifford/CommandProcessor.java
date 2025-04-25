package edu.austral.ingsis.clifford;

import java.util.List;
import java.util.ArrayList;

public class CommandProcessor implements FileSystemRunner {

  private final Filesystem filesystem;

  public CommandProcessor(Filesystem filesystem) {
    this.filesystem = filesystem;
  }

  @Override
  public List<String> executeCommands(List<String> commands) {
    List<String> output = new ArrayList<>();
    Command command;
    InmutableResponse response;
    for (String singleInput : commands) {
      String[] input = singleInput.split(" ");
      switch (input[0]) {
        case "ls":
          command = new ListDir(filesystem, input[1]);
          break;
        case "cd":
          command = new Change(filesystem, input[1]);
          break;
        case "touch":
          command = new Touch(filesystem, input[1]);
          break;
        case "mkdir":
          command = new MakeDir(filesystem, input[1]);
          break;
        case "rm":
          command = new Remove(filesystem, input);
          break;
        case "pwd":
          command = new PrintDir(filesystem);
          break;
        default:
          command = new EmptyCommand();
      }
      response = command.execute();
    }
  }

}
