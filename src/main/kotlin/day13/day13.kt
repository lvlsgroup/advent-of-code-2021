fun main() {
    Day13()
}

class Day13 : Runner() {
    override fun a() {
        val input = parseMatrixAndInstructions()
        var newMap = mutableMapOf<Int, MutableSet<Int>>()
        var currentMap = input.matrix

        // for problem a
        var instructionNumber = 0

        input.instructions.forEach {
            if (it.coordinate == "x") {
                val position = it.position
                currentMap.forEach { x, row ->
                    row.forEach { y ->
                        val newY = if (y > position) {
                            val distance = y - position
                            position - (distance)
                        } else {
                            y
                        }
                        val oldRow = newMap[x]
                        if (oldRow != null) {
                            oldRow.add(newY)
                        } else {
                            newMap[x] = mutableSetOf(newY)
                        }
                    }
                }
            } else {
                val position = it.position
                currentMap.forEach {
                    val newX = if (it.key > position) {
                        val distance = it.key - position
                        position - distance
                    } else {
                        it.key
                    }
                    val oldRow = newMap[newX]
                    if (oldRow != null) {
                        it.value.forEach { y ->
                            oldRow.add(y)
                        }
                    } else {
                        newMap[newX] = it.value.toMutableSet()
                    }
                }
            }
            if (instructionNumber == 0) {
                var sum = 0
                newMap.forEach { _, row ->
                    row.forEach {
                        sum++
                    }
                }
                print(sum)
            }
            instructionNumber++
            currentMap = newMap
            newMap = mutableMapOf()
        }
        println()
        for (x in 0..5) {
            val row = currentMap[x]
            for (y in 0..40) {
                if (row?.contains(y) == true) {
                    kotlin.io.print("x")
                } else {
                    kotlin.io.print(".")
                }
            }
            println()
        }
    }

    override fun b() {
        // solved in a
    }

    private fun parseMatrixAndInstructions(): Input {
        val reader = getReader()
        var nextLine = reader.readLine()
        val matrix = mutableMapOf<Int, MutableSet<Int>>()
        val instructions = mutableListOf<Fold>()
        var points = 0
        while (nextLine != "") {
            val coordinates = nextLine.split(",")
            val y = coordinates[0].toInt()
            val x = coordinates[1].toInt()
            val row = matrix.getOrPut(x) {
                mutableSetOf()
            }
            row.add(y)
            points++
            nextLine = reader.readLine()
        }
        nextLine = reader.readLine()
        while (nextLine != null) {
            val instructionSplit = nextLine.split(" ")
            val split = instructionSplit[2].split("=")
            val splitCoordinate = split[0]
            val splitPosition = split[1].toInt()
            instructions.add(Fold(splitCoordinate, splitPosition))
            nextLine = reader.readLine()
        }
        return Input(matrix, instructions)
    }

    data class Input(val matrix: MutableMap<Int, MutableSet<Int>>, val instructions: List<Fold>)
    data class Fold(val coordinate: String, val position: Int)


}
