package edu.austral.ingsis.clifford;

public class Touch implements Command {

  private final Filesystem filesystem;
  private final Directory dir;
  private final String filename;

  public Touch(Filesystem filesystem, String filename) {
    this.filesystem = filesystem;
    this.dir = filesystem.getPos();
    this.filename = filename;
  }

  public InmutableResponse execute() {
    return filesystem.addDocument(dir.path(), filename);
  }
}
