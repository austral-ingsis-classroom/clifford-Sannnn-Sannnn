package edu.austral.ingsis.clifford;

import java.util.Comparator;

public class FileComparator implements Comparator<File> {

  @Override
  public int compare(File o, File t1) {
    Comparator<String> fileComparator = Comparator.naturalOrder();
    if (o.isDirectory() == t1.isDirectory()) { // Both are the same file type -> Return the alphabetical first
      return fileComparator.compare(o.name(), t1.name());
    } else { // They are not the same type of file
      return o.isDirectory() ? 1 : -1;
    }
  }
}