fun main() {
    Day14()
}

class Day14 : Runner() {
    override fun a() {
        val input = parseInput()
        var currentCode = input.code.toCharArray().toList()
        for (i in 1..10) {
            val newCode = mutableListOf<Char>()
            var firstChar: Char?
            var secondChar: Char? = null
            currentCode.forEach {
                firstChar = secondChar
                secondChar = it
                if (firstChar != null) {
                    val matchToFind = firstChar.toString() + secondChar.toString()
                    val char = input.instructions[matchToFind]
                    newCode.add(char!!)
                }
                newCode.add(secondChar!!)
            }
            currentCode = newCode
        }

        val sortedLength = currentCode.groupBy { it }.values.map { it.size }.sorted()
        print(sortedLength[sortedLength.size - 1] - sortedLength[0], 2223)

    }

    override fun b() {
        val input = parseInput()
        val inputChars = input.code.toCharArray().toList()
        var pairCount = mutableMapOf<String, Long>()
        var firstChar: Char? = null
        var secondChar: Char? = null
        inputChars.forEach {
            firstChar = secondChar
            secondChar = it
            if (firstChar != null) {
                val matchToFind = firstChar.toString() + secondChar.toString()
                pairCount[matchToFind] = pairCount.getOrDefault(matchToFind, 0) + 1
            }
        }
        for (i in 1..40) {
            val nextPairCount = mutableMapOf<String, Long>()
            pairCount.forEach {
                val matchToFind = it.key
                val output = input.instructions[matchToFind]
                val firstString = matchToFind[0].toString() + output.toString()
                val secondString = output.toString() + matchToFind[1].toString()
                nextPairCount[firstString] = nextPairCount.getOrDefault(firstString, 0) + it.value
                nextPairCount[secondString] = nextPairCount.getOrDefault(secondString, 0) + it.value
            }
            pairCount = nextPairCount
        }

        val sum = mutableMapOf<Char, Long>()
        pairCount.forEach {
            val first = it.key[0]
            val second = it.key[1]
            sum[first] = sum.getOrDefault(first, 0) + it.value
            sum[second] = sum.getOrDefault(second, 0) + it.value
        }
        val sortedLength = sum.map {
            var newSum = it.value
            if (it.key == inputChars[0]) {
                newSum += 1 // compensate corners
            }
            if (it.key == inputChars[inputChars.size - 1]) {
                newSum += 1 // compensate corners
            }
            newSum
        }.sorted()
        print((sortedLength[sortedLength.size - 1] - sortedLength[0]) / 2, 2566282754493)
    }

    private fun parseInput(): Input {
        val reader = getReader()
        var nextLine = reader.readLine()
        val startCode = nextLine
        val instructions = mutableMapOf<String, Char>()
        reader.readLine()
        nextLine = reader.readLine()
        while (nextLine != null) {
            val split = nextLine.split(" -> ")
            instructions[split[0]] = split[1].toCharArray()[0]
            nextLine = reader.readLine()
        }
        return Input(instructions, startCode)
    }

    data class Input(val instructions: Map<String, Char>, val code: String)

}
