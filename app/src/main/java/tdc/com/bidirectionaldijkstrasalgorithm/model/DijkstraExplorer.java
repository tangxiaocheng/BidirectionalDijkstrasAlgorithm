package tdc.com.bidirectionaldijkstrasalgorithm.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class DijkstraExplorer {
  // visited, means we visited this node during exploring a node, we haven't fully explored this
  // node yet.
  Map<String, String> visitedMap;

  // explored, means we have fully explored this node, we have been to all its neighbors nodes.
  Set<String> exploredSet;

  // the shortest distance of a node to the init node.
  Map<String, Double> distanceMap;
  PriorityQueue<Edge> pq;
  Graph graph;

  public DijkstraExplorer(String initNode, Graph graph) {
    int n = graph.size();
    this.graph = graph;
    this.exploredSet = new HashSet<>(n);
    this.visitedMap = new HashMap<>(n);
    this.distanceMap = new HashMap<>(n);
    this.pq = new PriorityQueue<>((a, b) -> Double.compare(a.distance, b.distance));
    this.distanceMap.put(initNode, 0.0);
    visitedMap.put(initNode, null);
    this.pq.offer(new Edge(initNode, 0.0));
  }

  public String exploring(DijkstraExplorer waitingExplorer, Set<String> commonSet) {
    String exploringNode = null;
    if (pq.size() > 0) {
      Edge poll = pq.poll();
      exploringNode = poll.to;
      if (exploredSet.add(exploringNode)) {
        List<Edge> edgeList = graph.getEdges(exploringNode);
        if (edgeList != null) {
          for (Edge edge : edgeList) {
            String neighbor = edge.to;
            if (!exploredSet.contains(neighbor)) {
              double newCostToNeighbor = edge.distance + distanceMap.get(exploringNode);
              if (newCostToNeighbor < distanceMap.getOrDefault(neighbor, Double.MAX_VALUE)) {
                distanceMap.put(neighbor, newCostToNeighbor);
                visitedMap.put(neighbor, exploringNode);
                pq.add(new Edge(neighbor, newCostToNeighbor));
                if (waitingExplorer.distanceMap.containsKey(neighbor)) {
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
