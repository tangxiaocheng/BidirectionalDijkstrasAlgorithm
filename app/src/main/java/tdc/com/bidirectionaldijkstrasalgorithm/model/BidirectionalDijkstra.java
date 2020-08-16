package tdc.com.bidirectionaldijkstrasalgorithm.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

class BidirectionalDijkstra {

  Graph graph;
  Graph reverseGraph;

  public BidirectionalDijkstra(Graph graph, boolean directedGraph) {
    this.graph = graph;
    this.graph = directedGraph ? graph.reverseGraph() : graph;
  }

  public BidirectionalDijkstra(Graph graph) {
    this.graph = graph;
    this.reverseGraph = graph;
  }

  public Res runBidirectionalDijkstra(String start, String end) {
    DijkstraExplorer startExplorer = new DijkstraExplorer(start, graph);
    DijkstraExplorer endExplorer = new DijkstraExplorer(end, reverseGraph);
    Set<String> commonSet = new HashSet<>();
    while (true) {
      String exploredNode = startExplorer.exploring(commonSet, endExplorer);
      if (exploredNode == null || endExplorer.exploredSet.contains(exploredNode)) {
        break;
      }

      exploredNode = endExplorer.exploring(commonSet, startExplorer);
      if (exploredNode == null || startExplorer.exploredSet.contains(exploredNode)) {
        break;
      }
    }

    Res res = new Res(Double.POSITIVE_INFINITY, new LinkedList<>());
    if (commonSet.size() > 0) {
      double shortestDistance = Double.POSITIVE_INFINITY;
      String realMeetingPoint = null;
      for (String node : commonSet) {
        double temp = startExplorer.distanceMap.get(node) + endExplorer.distanceMap.get(node);
        if (temp < shortestDistance) {
          shortestDistance = temp;
          realMeetingPoint = node;
        }
      }
      res.shortestDistance = shortestDistance;

      String cur = realMeetingPoint;
      while (cur != null) {
        res.path.addFirst(cur);
        cur = startExplorer.pathMap.get(cur);
      }
      cur = realMeetingPoint;
      res.path.pollLast();
      while (cur != null) {
        res.path.addLast(cur);
        cur = endExplorer.pathMap.get(cur);
      }
    }
    return res;
  }

  static class Res {
    double shortestDistance;
    LinkedList<String> path;

    public Res(double shortestDistance, LinkedList<String> path) {
      this.shortestDistance = shortestDistance;
      this.path = path;
    }
  }
}
