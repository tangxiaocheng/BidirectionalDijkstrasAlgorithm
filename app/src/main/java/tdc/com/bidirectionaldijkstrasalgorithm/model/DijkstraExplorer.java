package tdc.com.bidirectionaldijkstrasalgorithm.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class DijkstraExplorer {
  Graph graph;
  Map<String, String> pathMap;
  Set<String> exploredSet;
  Map<String, Double> distanceMap;

  PriorityQueue<Edge> pq;

  public DijkstraExplorer(String initNode, Graph graph) {
    int n = graph.size();
    pq = new PriorityQueue<>(n, (a, b) -> (Double.compare(a.distance, b.distance)));
    this.graph = graph;
    pathMap = new HashMap<>(n);
    distanceMap = new HashMap<>(n);
    exploredSet = new HashSet<>();
    pathMap.put(initNode, null);
    distanceMap.put(initNode, 0.0);
    pq.offer(new Edge(initNode, 0.0));
  }

  public String exploring(Set<String> commonSet, DijkstraExplorer endExplorer) {
    String exploringNode = null;
    if (pq.size() > 0) {
      Edge poll = pq.poll();
      exploringNode = poll.to;
      if (exploredSet.add(exploringNode)) {
        List<Edge> edgeList = graph.getEdgeList(exploringNode);
        if (edgeList != null) {
          for (Edge edge : edgeList) {
            String neighbor = edge.to;
            if (!exploredSet.contains(neighbor)) {
              double newDistance = distanceMap.get(exploringNode) + edge.distance;
              if (newDistance < distanceMap.getOrDefault(neighbor, Double.MAX_VALUE)) {
                distanceMap.put(neighbor, newDistance);
                pathMap.put(neighbor, exploringNode);
                pq.offer(new Edge(neighbor, newDistance));
                if (endExplorer.distanceMap.containsKey(neighbor)) {
                  commonSet.add(neighbor);
                }
              }
            }
          }
        }
      }
    }

    return exploringNode;
  }
}
