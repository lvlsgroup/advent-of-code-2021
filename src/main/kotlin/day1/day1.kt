fun main() {
    Day1()
}

class Day1 : Runner() {
    override fun a() {
        val reader = getReader()
        var currentNumber = reader.readInt()
        var inreases = 0
        var lastNumber = Integer.MAX_VALUE
        while (currentNumber != null) {
            if (currentNumber > lastNumber) {
                inreases++
            }
            lastNumber = currentNumber
            currentNumber = reader.readInt()
        }
        print(inreases, 1791)
    }

    override fun b() {
        val reader = getReader()
        val ints = mutableListOf<Int>()
        var currentNumber = reader.readInt()
        currentNumber?.let { ints.add(it) }
        var inreases = 0
        while (currentNumber != null) {
            if (ints.size > 3 && ints.takeLast(3).sum() > (ints.takeLast(4).sum() - ints.takeLast(1).sum())) {
                inreases++
            }
            currentNumber = reader.readInt()
            currentNumber?.let { ints.add(it) }
        }
        print(inreases, 1963088820)
    }
}