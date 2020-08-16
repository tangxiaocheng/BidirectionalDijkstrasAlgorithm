package tdc.com.bidirectionaldijkstrasalgorithm.model

import org.junit.Test

public class BidirectionalDijkstraTest {
    @Test
    void testRunBidirectionalDijkstra() {
        Graph graph = new Graph();
        BidirectionalDijkstra bidirectionalDijkstra = new BidirectionalDijkstra(graph, true);

    }
}
