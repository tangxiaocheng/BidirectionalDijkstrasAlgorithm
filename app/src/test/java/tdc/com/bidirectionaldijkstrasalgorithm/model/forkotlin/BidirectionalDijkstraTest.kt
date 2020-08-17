package tdc.com.bidirectionaldijkstrasalgorithm.model.forkotlin

import com.google.common.truth.Truth
import org.junit.Test

import java.util.*
import java.util.Collections.sort

class BidirectionalDijkstraTest {
    private fun Int.generateDirectedGraph(
        numOfNodes: Int,
        numOfEdges: Int,
        distanceUpBound: Int,
        directedGraph: Boolean
    ) {
        val random = Random()
        val graph = Graph(numOfNodes)
        val nodes = generateNodesSourceArr(numOfNodes)
        for (i in 0 until numOfEdges) {
            val nodePair = generateOneEdge(nodes, random)
            val from = nodePair[0]
            val to = nodePair[1]
            val cost = random.nextInt(distanceUpBound + 1)
            if (directedGraph) {
                graph.addDirectedEdge(from.toString(), to.toString(), cost.toDouble())
            } else {
                graph.addUndirectedEdge(from.toString(), to.toString(), cost.toDouble())
            }
        }
        val bidirectionalDijkstras = BidirectionalDijkstrasAlgorithm(graph, directedGraph)
        for (i in 0..this) {
            val randomNodePair = getRandomTwoNodes(graph.nodeSet)
            val start: String? = randomNodePair[0]
            val end: String? = randomNodePair[1]
            val (thePath: LinkedList<String>, shortestDistance: Double) = bidirectionalDijkstras.runBidirectionalDijkstra(start!!, end!!)
            Truth.assertThat(shortestDistance)
                .isEqualTo(
                    getShortestDistanceFromThePath(
                        graph,
                        thePath
                    )
                )
            println(thePath)
        }
    }

    private fun getShortestDistanceFromThePath(graph: Graph, thePath: LinkedList<String>): Double {
        if (thePath.size == 0) {
            return Double.POSITIVE_INFINITY
        }
        var sumPath = 0.0
        val iterator = thePath.iterator()
        if (iterator.hasNext()) {
            var from: String = iterator.next()
            while (iterator.hasNext()) {
                val to = iterator.next()
                val edges =
                    graph.getEdges(from)
                // why we sort the edges here, because in this graph which we randomly generated, it might
                // exist parallel edges, if so, the distance might be different. So we just fetch the
                // minimum.
                sort(edges!!) { a: Edge, b: Edge ->
                    a.distance.compareTo(b.distance)
                }
                for (edge in edges) {
                    if (edge.to == to) {
                        sumPath += edge.distance
                        break
                    }
                }
                from = to
            }
        }
        return sumPath
    }

    @Test
    fun testBidirectionalDijkstrasAlgorithmForDirectedGraph() {
        100.generateDirectedGraph(400, 8000, 800, true)
    }

    @Test
    fun testBidirectionalDijkstrasAlgorithmForUndirectedGraph() {
        100.generateDirectedGraph(400, 8000, 800, false)
    }

    companion object {
        fun getRandomTwoNodes(nodeSet: MutableSet<String?>): Array<String?> {
            // using reservoir sample to choose two random nodes.
            val random = Random()
            val pair: Array<String?> = arrayOfNulls(2)
            val iterator = nodeSet.iterator()
            pair[0] = iterator.next()
            pair[1] = iterator.next()
            val k = 2
            var j = k
            while (iterator.hasNext()) {
                j++
                val node = iterator.next()
                val randomI = random.nextInt(j)
                if (randomI < k) {
                    pair[randomI] = node
                }
            }
            return pair
        }

        private fun generateOneEdge(nodes: IntArray, random: Random): IntArray {
            // using reservoir sample to choose two random node to form an edge.
            val k = 2
            val nodePair = IntArray(k)
            System.arraycopy(nodes, 0, nodePair, 0, k)
            for (j in k until nodes.size) {
                val temp = nodes[j]
                val randomIdx = random.nextInt(j + 1)
                if (randomIdx < k) {
                    nodePair[randomIdx] = temp
                }
            }
            return nodePair
        }

        private fun generateNodesSourceArr(numOfNodes: Int): IntArray {
            val nodes = IntArray(numOfNodes)
            for (i in nodes.indices) {
                nodes[i] = i
            }
            return nodes
        }
    }
}