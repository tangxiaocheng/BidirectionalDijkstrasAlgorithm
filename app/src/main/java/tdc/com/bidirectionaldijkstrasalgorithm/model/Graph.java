package tdc.com.bidirectionaldijkstrasalgorithm.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Graph {
  Map<String, List<Edge>> graphMap;

  public Graph(int n) {
    graphMap = new HashMap<>(n);
  }

  public Graph() {
    graphMap = new HashMap<>();
  }

  void addUndirectedEdge(String from, String to, double distance) {
    addDirectedEdge(from, to, distance);
    addDirectedEdge(to, from, distance);
  }

  void addDirectedEdge(String from, String to, double distance) {
    graphMap.computeIfAbsent(from, x -> new LinkedList<>()).add(new Edge(to, distance));
  }

  public List<Edge> getEdgeList(String node) {
    return graphMap.get(node);
  }

  public int size() {
    return graphMap.size();
  }

  public Graph reverseGraph() {
    Graph reverseGraph = new Graph(size());
    for (String from : graphMap.keySet()) {
      for (Edge edge : graphMap.get(from)) {
        String to = edge.to;
        reverseGraph.addDirectedEdge(to, from, edge.distance);
      }
    }
    return reverseGraph;
  }
}
