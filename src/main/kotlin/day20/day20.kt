fun main() {
    Day20()
}

class Day20 : Runner() {

    override fun a() {
        val input = parseInput()
        val increasedMatrix = increase(input.matrix)
        val extendedMatrix = extend(increasedMatrix, input.imageDecoder, false)
        var extended = extend(extendedMatrix, input.imageDecoder, true)
        print(countActive(extended))
        for (i in 1..48) {
            extended = extend(extended, input.imageDecoder, i % 2 == 0)
        }
        print(countActive(extended))
    }

    private fun countActive(matrix: List<List<Char>>): Int {
        var amount = 0
        matrix.forEachIndexed { index1, list ->
            list.forEachIndexed { index2, char ->
                if (char == '#') {
                    amount++
                }
            }
        }
        return amount
    }

    private fun printMatrix(matrix: List<List<Char>>) {
        matrix.forEach {
            it.forEach {
                kotlin.io.print(it)
            }
            println()
        }
    }

    private fun extend(matrix: List<List<Char>>, imageDecoder: CharArray, isEven: Boolean): List<List<Char>> {
        return matrix.mapIndexed { index1, list ->
            list.mapIndexed { index2, char ->
                var binary = ""
                for (x in index1 - 1..index1 + 1) {
                    for (y in index2 - 1..index2 + 1) {
                        if (x < 0) {
                            binary += 0
                        } else if (y < 0) {
                            binary += 0
                        } else if (y >= matrix.size) {
                            binary += 0
                        } else if (x >= list.size) {
                            binary += 0
                        } else {
                            if (matrix[x][y] == '#') {
                                binary += 1
                            } else {
                                binary += 0
                            }
                        }
                    }
                }
                val lookup = binary.binaryToInt()
                if (isEven && (index1 == 0 || index2 == 0 || index1 == matrix.size - 1 || index2 == list.size - 1)) {
                    '.'
                } else {
                    imageDecoder[lookup]
                }
            }
        }
    }

    override fun b() {

    }

    private fun increase(matrix: List<List<Char>>): List<List<Char>> {
        val result = mutableListOf<MutableList<Char>>()
        for (i in -150..(matrix.size + 150)) {
            val row = if (matrix.size > i && i >= 0) {
                matrix[i]
            } else {
                matrix[0].map { '.' }
            }
            val newRow = mutableListOf<Char>()
            for (j in -150..(matrix[0].size + 150)) {
                val char = if (row.size > j && j >= 0) {
                    row[j]
                } else {
                    '.'
                }
                newRow.add(char)
            }
            result.add(newRow)
        }
        return result
    }

    private fun parseInput(): Input {
        val reader = getReader()
        val imageDecoder = reader.readLine().toCharArray()
        reader.readLine()
        var nextLine = reader.readLine()
        val matrix = mutableListOf<MutableList<Char>>()
        while (nextLine != null) {
            val line = nextLine.toCharArray().toMutableList()
            matrix.add(line)
            nextLine = reader.readLine()
        }

        return Input(imageDecoder, matrix)
    }

    data class Input(
        val imageDecoder: CharArray,
        val matrix: List<List<Char>>,
    )

    fun String.binaryToInt() = toInt(2)
}


