package tdc.com.bidirectionaldijkstrasalgorithm.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Graph {

  private Map<String, List<Edge>> graphMap;

  Graph() {
    graphMap = new HashMap<>();
  }

  Graph(int n) {
    graphMap = new HashMap<>(n);
  }

  List<Edge> getEdges(String node) {
    return graphMap.get(node);
  }

  void addDirectedEdge(String from, String to, double distance) {
    graphMap.computeIfAbsent(from, x -> new LinkedList<>()).add(new Edge(to, distance));
  }

  void addUndirectedEdge(String from, String to, double distance) {
    addDirectedEdge(from, to, distance);
    addDirectedEdge(to, from, distance);
  }

  public Graph reverseGraph() {
    Graph reverseGraph = new Graph(graphMap.size());
    for (String from : graphMap.keySet()) {
      for (Edge edge : graphMap.get(from)) {
        reverseGraph.addDirectedEdge(edge.to, from, edge.distance);
      }
    }
    return reverseGraph;
  }

  public int size() {
    return graphMap.size();
  }

  public Set<String> getNodeSet() {
    return graphMap.keySet();
  }


}
