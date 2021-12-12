fun main() {
    Day12()
}

class Day12 : Runner() {
    override fun a() {
        val graph = parseInput()
        print(
            graph.startNode.calculatePaths(
                endNode = graph.endNode,
                path = null,
                smallVisited = mutableSetOf(),
                twice = false,
                visitedTwiceActivated = false
            ), 3510
        )
    }

    override fun b() {
        val graph = parseInput()
        print(
            graph.startNode.calculatePaths(
                endNode = graph.endNode,
                path = null,
                smallVisited = mutableSetOf(),
                twice = false,
                visitedTwiceActivated = true
            ), 122880
        )
    }


    private fun parseInput(): Graph {
        val reader = getReader()
        var nextLine = reader.readLine()
        val graph = Graph()
        while (nextLine != null) {
            val nodes = nextLine.split("-")
            val firstNode = nodes[0]
            val secondNode = nodes[1]
            graph.addNode(firstNode, secondNode)
            nextLine = reader.readLine()
        }
        return graph
    }

    class Graph {
        fun addNode(firstNode: String, secondNode: String) {
            val node1 = if (nodes.contains(firstNode)) {
                nodes[firstNode]!!
            } else {
                addNode(firstNode)
            }
            val node2 = if (nodes.contains(secondNode)) {
                nodes[secondNode]!!
            } else {
                addNode(secondNode)
            }
            node1.addEdge(node2)
            node2.addEdge(node1)
        }

        private fun addNode(name: String): Node {
            val isStart = name == "start"
            val isEnd = name == "end"
            val node = Node(name, name == name.lowercase(), isStart = isStart)
            if (isStart) {
                startNode = node
            }
            if (isEnd) {
                endNode = node
            }
            nodes[name] = node
            return node
        }

        lateinit var startNode: Node
        lateinit var endNode: Node
        private var nodes = mutableMapOf<String, Node>()
    }

    data class Node(
        val name: String,
        val small: Boolean,
        val isStart: Boolean
    ) {
        val nodes = mutableListOf<Node>()
        fun addEdge(node2: Node) {
            nodes.add(node2)
        }

        fun calculatePaths(
            endNode: Node,
            path: String?,
            smallVisited: MutableSet<String>,
            twice: Boolean,
            visitedTwiceActivated: Boolean
        ): Int {
            var visitedTwice = twice
            if (smallVisited.contains(this.name)) {
                if (!visitedTwice && visitedTwiceActivated && !isStart) {
                    visitedTwice = true
                } else {
                    return 0
                }
            }
            if (endNode.name == this.name) {
                if (path != null) println("$path${this.name}")
                return 1
            }
            if (small) {
                smallVisited.add(this.name)
            }
            return nodes.sumOf {
                it.calculatePaths(
                    endNode = endNode,
                    path = path?.let { "$path${this.name}," },
                    smallVisited = smallVisited.toMutableSet(),
                    twice = visitedTwice,
                    visitedTwiceActivated = visitedTwiceActivated
                )
            }
        }
    }


}
