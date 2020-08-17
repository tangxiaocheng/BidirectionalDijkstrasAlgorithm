package tdc.com.bidirectionaldijkstrasalgorithm.model;

import java.util.LinkedList;

class Res {
  LinkedList<String> thePath;
  double shortestDistance;

  public Res(LinkedList<String> thePath, double shortestDistance) {
    this.thePath = thePath;
    this.shortestDistance = shortestDistance;
  }
}
