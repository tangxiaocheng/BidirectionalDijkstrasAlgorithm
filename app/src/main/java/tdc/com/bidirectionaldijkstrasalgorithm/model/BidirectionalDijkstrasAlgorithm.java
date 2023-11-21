package tdc.com.bidirectionaldijkstrasalgorithm.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
// rewrite in kotlin
class BidirectionalDijkstrasAlgorithm {

  private Graph reverseGraph;
  private Graph graph;
  private DijkstraExplorer startExplorer;
  private DijkstraExplorer endExplorer;

  public BidirectionalDijkstrasAlgorithm(Graph graph, boolean directedGraph) {
    this.graph = graph;
    this.reverseGraph = directedGraph ? graph.reverseGraph() : graph;
  }

  public BidirectionalDijkstrasAlgorithm(Graph graph) {
    this.graph = graph;
    this.reverseGraph = graph;
  }

  Res runBidirectionalDijkstra(String start, String end) {

    startExplorer = new DijkstraExplorer(start, graph);
    endExplorer = new DijkstraExplorer(end, reverseGraph);
    Set<String> commonSet = new HashSet<>(graph.size());
    Res res = new Res(new LinkedList<>(), Double.POSITIVE_INFINITY);
    while (true) {
      String exploringNode = startExplorer.exploring(endExplorer, commonSet);
      if (exploringNode == null || endExplorer.exploredSet.contains(exploringNode)) {
        break;
      }
      exploringNode = endExplorer.exploring(startExplorer, commonSet);
      if (exploringNode == null || startExplorer.exploredSet.contains(exploringNode)) {
        break;
      }
    }
    if (commonSet.size() > 0) {
      extract2TheRes(commonSet, res);
    }
    return res;
  }

  private void extract2TheRes(Set<String> commonSet, Res res) {
    double shortPath = Double.MAX_VALUE;
    String realMeetingNode = null;
    for (String node : commonSet) {
      double temp = endExplorer.distanceMap.get(node) + startExplorer.distanceMap.get(node);
      if (temp < shortPath) {
        shortPath = temp;
        realMeetingNode = node;
      }
    }
    res.shortestDistance = shortPath;
    extractPath(realMeetingNode, res.thePath);
  }

  private void extractPath(String realMeetingNode, LinkedList<String> thePath) {
    String cur = realMeetingNode;
    // add from the meeting node to start.
    while (cur != null) {
      thePath.addFirst(cur);
      cur = startExplorer.visitedMap.get(cur);
    }

    // poll the meeting node, otherwise it will add twice to the thePath list
    thePath.pollLast();
    cur = realMeetingNode;

    // add from the meeting node to end
    while (cur != null) {
      thePath.addLast(cur);
      cur = endExplorer.visitedMap.get(cur);
    }
  }
}
