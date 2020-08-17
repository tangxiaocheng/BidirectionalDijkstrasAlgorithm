package tdc.com.bidirectionaldijkstrasalgorithm.model.forkotlin

import java.util.*

data class Graph(val n: Int) {
    private val graphMap: MutableMap<String?, MutableList<Edge>> = HashMap(n)

    fun getEdges(node: String): List<Edge>? {
        return graphMap[node]
    }

    fun addDirectedEdge(from: String, to: String, distance: Double) {
        graphMap.computeIfAbsent(from) { LinkedList() }.add(Edge(to, distance))
    }

    fun addUndirectedEdge(from: String, to: String, distance: Double) {
        addDirectedEdge(from, to, distance)
        addDirectedEdge(to, from, distance)
    }

    fun reverseGraph(): Graph {
        val reverseGraph = Graph(graphMap.size)
        for (from in graphMap.keys) {
            for (edge in graphMap[from]!!) {
                reverseGraph.addDirectedEdge(edge.to, from!!, edge.distance)
            }
        }
        return reverseGraph
    }

    fun size(): Int {
        return graphMap.size
    }

    val nodeSet: MutableSet<String?>
        get() = graphMap.keys
}