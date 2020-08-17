package tdc.com.bidirectionaldijkstrasalgorithm.model.forkotlin

import java.util.*

class BidirectionalDijkstrasAlgorithm(private val graph: Graph, directedGraph: Boolean) {
    private val reverseGraph: Graph = if (directedGraph) {
        graph.reverseGraph()
    } else {
        graph
    }
    private lateinit var startExplorer: DijkstraExplorer
    private lateinit var endExplorer: DijkstraExplorer


    fun runBidirectionalDijkstra(start: String, end: String): Res {
        startExplorer = DijkstraExplorer(start, graph)
        endExplorer = DijkstraExplorer(end, reverseGraph)
        val commonSet: MutableSet<String> =
            HashSet(graph.size())
        val res = Res(LinkedList(), Double.POSITIVE_INFINITY)
        while (true) {
            var exploringNode: String? = startExplorer.exploring(endExplorer, commonSet)
            if (exploringNode == null || endExplorer.exploredSet.contains(exploringNode)) {
                break
            }
            exploringNode = endExplorer.exploring(startExplorer, commonSet)
            if (exploringNode == null || startExplorer.exploredSet.contains(exploringNode)) {
                break
            }
        }
        if (commonSet.size > 0) {
            extract2TheRes(commonSet, res)
        }

        return res
    }

    private fun extract2TheRes(commonSet: Set<String>, res: Res) {
        var shortPath = Double.MAX_VALUE
        var realMeetingNode = "any one"
        for (node in commonSet) {
            val temp =
                endExplorer.distanceMap[node]!! + startExplorer.distanceMap[node]!!
            if (temp < shortPath) {
                shortPath = temp
                realMeetingNode = node
            }
        }
        res.shortestDistance = shortPath
        extractPath(realMeetingNode, res.thePath)
    }

    private fun extractPath(realMeetingNode: String, thePath: LinkedList<String>) {
        var cur: String? = realMeetingNode
        // add from the meeting node to start.
        while (null != cur) {
            thePath.addFirst(cur)
            cur = startExplorer.visitedMap[cur]
        }

        // poll the meeting node, otherwise it will add twice to the thePath list
        thePath.pollLast()
        cur = realMeetingNode

        // add from the meeting node to end
        while (cur != null) {
            thePath.addLast(cur)
            cur = endExplorer.visitedMap[cur]
        }
    }
}