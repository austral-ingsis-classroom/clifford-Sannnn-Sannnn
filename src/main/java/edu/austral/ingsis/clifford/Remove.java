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
    this.filename = parameters.length < 3 ? parameters[1] : parameters [2];
    this.recursive = parameters.length > 2 && parameters[1].equals("--recursive");
    };
  
  public InmutableResponse execute() {
    return filesystem.removeFile(dir.path(), filename, recursive);
  }
  
}