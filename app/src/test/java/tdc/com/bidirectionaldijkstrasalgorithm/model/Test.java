package tdc.com.bidirectionaldijkstrasalgorithm.model;

import static com.google.common.truth.Truth.assertThat;

// for assertions on Java 8 types (Streams and java.util.Optional)
// for assertions on Java 8 types (Streams and java.util.Optional)
public class Test {
  @org.junit.Test
  public void testRunBidirectionalDijkstra() {
    Graph graph = new Graph(100);

    BidirectionalDijkstra bidirectionalDijkstra = new BidirectionalDijkstra(graph, true);
    String start = "";
    String end = "";
    BidirectionalDijkstra.Res res = bidirectionalDijkstra.runBidirectionalDijkstra(start, end);
    double shortestPath = 0;
    assertThat(res.shortestDistance).isEqualTo(shortestPath);
  }
}
