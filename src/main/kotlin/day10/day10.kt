fun main() {
    Day10()
}

class Day10 : Runner() {
    override fun a() {
        val reader = getReader()
        var nextLine = reader.readLine()
        val stateHolder = StateHolder()
        while (nextLine != null) {
            val queue = mutableListOf<Char>()
            nextLine.forEach {
                when (it) {
                    '(' -> {
                        queue.add(it)
                    }
                    '[' -> {
                        queue.add(it)
                    }
                    '{' -> {
                        queue.add(it)
                    }
                    '<' -> {
                        queue.add(it)
                    }
                    ')' -> {
                        stateHolder.updateState('(', 3, queue)
                    }
                    ']' -> {
                        stateHolder.updateState('[', 57, queue)
                    }
                    '}' -> {
                        stateHolder.updateState('{', 1197, queue)
                    }
                    '>' -> {
                        stateHolder.updateState('<', 25137, queue)
                    }
                }
            }
            stateHolder.calculateScoreAndReset(queue)
            nextLine = reader.readLine()
        }

        print(stateHolder.sumA)
        print(stateHolder.calculateB())
    }

    override fun b() {
        // calculated in a()
    }
}

class StateHolder {
    var sumA = 0L
    val scoresB = mutableListOf<Long>()
    var corruptedSum: Long? = null
    var incomplete = false

    private fun reset() {
        corruptedSum = null
        incomplete = false
    }

    fun updateState(validChar: Char, sumWhenCorrupted: Long, queue: MutableList<Char>) {
        val checkChar = queue.removeLastOrNull()
        if (checkChar == null) {
            incomplete = true
        } else if (checkChar == validChar) {
            // all fine
        } else if (corruptedSum == null) {
            corruptedSum = sumWhenCorrupted
        }
    }

    fun calculateScoreAndReset(queue: MutableList<Char>) {
        if (corruptedSum != null) {
            sumA += corruptedSum!!
        } else {
            var sum = 0L
            queue.reversed().forEach {
                sum *= 5L
                when (it) {
                    '(' -> {
                        sum += 1L
                    }
                    '[' -> {
                        sum += 2L
                    }
                    '{' -> {
                        sum += 3L
                    }
                    '<' -> {
                        sum += 4L
                    }
                }
            }
            scoresB.add(sum)
        }
        reset()
    }

    fun calculateB(): Long {
        return scoresB.sorted()[scoresB.size / 2]
    }

}
