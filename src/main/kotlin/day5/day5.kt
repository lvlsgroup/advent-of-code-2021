fun main() {
    Day5()
}

class Day5 : Runner() {
    override fun a() {
        val matrix = fillMatrix(includeDiagonal = false)
        print(calculateScore(matrix))
    }

    override fun b() {
        val matrix = fillMatrix(includeDiagonal = true)
        print(calculateScore(matrix))
    }

    private fun fillMatrix(includeDiagonal: Boolean): Array<IntArray> {
        val reader = getReader()
        val matrix = Array(5000) { IntArray(5000) }
        var nextLine = reader.readLine()
        while (nextLine != null) {
            val lineSplit = nextLine.split(" -> ")
            val point1 = lineSplit[0].split(",").map { it.toInt() }
            val point2 = lineSplit[1].split(",").map { it.toInt() }
            val x1 = point1[0]
            val x2 = point2[0]
            val y1 = point1[1]
            val y2 = point2[1]
            if (x1 == x2) {
                // vertical line
                for (y in Math.min(y1,y2)..Math.max(y1, y2)) {
                    matrix[x1][y] += 1
                }
            }
            if (y1 == y2) {
                // horizontal line
                for (x in Math.min(x1, x2)..Math.max(x1, x2)) {
                    matrix[x][y1] += 1
                }
            }
            if (includeDiagonal && y1 != y2 && x1 != x2) {
                val y11 = minOf(y1, y2)
                val x11 = minOf(x1, x2)
                val x22 = maxOf(x1, x2)
                var counter = 0
                val fromY = if(x1 < x2) y1 else y2
                val increase = if(fromY == Math.min(y1, y2)) 1 else -1

                for (x in Math.min(x1, x2)..Math.max(x1, x2)) {
                    val nextX = x
                    val nextY = fromY + counter*increase
                    matrix[nextX][nextY] += 1
                    counter++
                }
            }
            nextLine = reader.readLine()
        }
        return matrix
    }

    private fun calculateScore(matrix: Array<IntArray>): Int {
        return matrix.sumOf { it.filter { it > 1 }.map { 1 }.sum() }
    }
}
