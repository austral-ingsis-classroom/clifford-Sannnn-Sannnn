package edu.austral.ingsis.clifford;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class Remove implements Command {

  private final Filesystem filesystem;
  private final String filename;
  private final Directory dir;
  private final boolean recursive;
  
  public Remove(Filesystem filesystem, String filename, boolean recursive) {
    this.filesystem = filesystem;
    this.dir = filesystem.getPos();
    this.filename = filename;
    this.recursive = recursive;
  }

  public Remove(Filesystem filesystem, String[] parameters) {
    this.filesystem = filesystem;
    this.dir = filesystem.getPos();
    this.filename = parameters[parameters.length - 1];
    this.recursive = parameters.length != 1;
  }
  
  public InmutableResponse execute() {
    if (recursive) {
      return filesystem.removeDir(dir.path(), filename);
    } else {
      return filesystem.removeDoc(dir.path(), filename);
    }
  }
  
}