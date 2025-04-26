package edu.austral.ingsis.clifford;

public class MakeDir implements Command {

  private final Filesystem filesystem;
  private final String filename;
  private final Directory dir;

  public MakeDir(Filesystem filesystem, String filename) {
    this.filesystem = filesystem;
    this.dir = filesystem.getPos();
    this.filename = filename;
  }

  public InmutableResponse execute() {
    return filesystem.addDirectory(dir.path(), filename);
  }
}
