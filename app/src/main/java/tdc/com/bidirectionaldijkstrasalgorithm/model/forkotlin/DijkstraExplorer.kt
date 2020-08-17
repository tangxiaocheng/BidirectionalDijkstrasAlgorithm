package tdc.com.bidirectionaldijkstrasalgorithm.model.forkotlin

import java.util.*

class DijkstraExplorer(initNode: String, graph: Graph) {
    // visited, means we visited this node during exploring a node, we haven't fully explored this
    // node yet.
    val visitedMap: MutableMap<String, String?>

    // explored, means we have fully explored this node, we have been to all its neighbors nodes.
    val exploredSet: MutableSet<String>

    // the shortest distance of a node to the init node.
    val distanceMap: MutableMap<String, Double>
    private val pq: PriorityQueue<Edge>
    private val graph: Graph
    fun exploring(waitingExplorer: DijkstraExplorer, commonSet: MutableSet<String>): String? {
        var exploringNode: String? = null
        if (pq.size > 0) {
            val poll = pq.poll()
            exploringNode = poll!!.to
            if (exploredSet.add(exploringNode)) {
                val edgeList = graph.getEdges(exploringNode)
                if (null != edgeList) {
                    for (edge in edgeList) {
                        val neighbor = edge.to
                        if (neighbor !in exploredSet) {
                            val newCostToNeighbor = edge.distance + distanceMap[exploringNode]!!
                            if (newCostToNeighbor < distanceMap.getOrDefault(
                                    neighbor,
                                    Double.MAX_VALUE
                                )
                            ) {
                                distanceMap[neighbor] = newCostToNeighbor
                                visitedMap[neighbor] = exploringNode
                                pq.add(Edge(neighbor, newCostToNeighbor))
                                if (waitingExplorer.distanceMap.containsKey(neighbor)) {
                                    commonSet.add(neighbor)
                                }
                            }
                        }
                    }

                }
            }
        }
        return exploringNode
    }

    init {
        val n = graph.size()
        this.graph = graph
        exploredSet = HashSet(n)
        visitedMap = HashMap(n)
        distanceMap = HashMap(n)
        pq =
            PriorityQueue { a, b ->
                a.distance.compareTo(b.distance)
            }
        distanceMap[initNode] = 0.0
        visitedMap[initNode] = null
        pq.offer(Edge(initNode, 0.0))
    }
}