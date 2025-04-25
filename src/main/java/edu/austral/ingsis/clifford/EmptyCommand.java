package edu.austral.ingsis.clifford;

public class EmptyCommand implements Command {

  // This class represents empty ro non-valid commands

  public EmptyCommand() {}

  @Override
  public InmutableResponse execute() {
    return null;
  }
}
