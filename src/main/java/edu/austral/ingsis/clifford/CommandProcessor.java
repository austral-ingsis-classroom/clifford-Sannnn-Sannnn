package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;

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
      command =
          switch (input[0]) {
            case "ls" -> new ListDir(filesystem, input);
            case "cd" -> new ChangeDir(filesystem, input[1]);
            case "touch" -> new Touch(filesystem, input[1]);
            case "mkdir" -> new MakeDir(filesystem, input[1]);
            case "rm" -> new Remove(filesystem, input);
            case "pwd" -> new PrintDir(filesystem);
            default -> new EmptyCommand();
          };
      response = command.execute();
      output.add(response.message());
    }
    return output;
  }
}
