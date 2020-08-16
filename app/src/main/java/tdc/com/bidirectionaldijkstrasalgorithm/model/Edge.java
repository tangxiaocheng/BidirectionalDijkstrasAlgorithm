package tdc.com.bidirectionaldijkstrasalgorithm.model;

class Edge {
  String to;
  double distance;

  public Edge(String to, double distance) {
    this.to = to;
    this.distance = distance;
  }
}
