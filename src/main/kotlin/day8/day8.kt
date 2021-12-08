fun main() {
    Day8()
}

class Day8 : Runner() {
    override fun a() {
        val reader = getReader()
        var sum = 0
        var nextLine = reader.readLine()
        while (nextLine != null) {
            val split = nextLine.split(" | ")
            val outputs = split[1].split(" ")
            sum += outputs.filter {
                when (it.length) {
                    2, 3, 4, 7 -> true
                    else -> false
                }
            }.size
            nextLine = reader.readLine()
        }
        print(sum, 521)
    }

    override fun b() {
        val reader = getReader()
        var totalSum = 0
        var nextLine = reader.readLine()
        while (nextLine != null) {
            val split = nextLine.split(" | ")
            val signals = split[0].split(" ").map { it.toCharArray().sortedBy { it }.joinToString("") }
            val output = split[1].split(" ").map { it.toCharArray().sortedBy { it }.joinToString("") }
            val all = signals.toMutableList().apply {
                addAll(output)
            }
            var sum = ""
            output.forEach {
                when (it) {
                    findLetter0(all) -> {
                        sum += 0
                    }
                    findLetter1(all) -> {
                        sum += 1
                    }
                    findLetter2(all) -> {
                        sum += 2
                    }
                    findLetter3(all) -> {
                        sum += 3
                    }
                    findLetter4(all) -> {
                        sum += 4
                    }
                    findLetter5(all) -> {
                        sum += 5
                    }
                    findLetter6(all) -> {
                        sum += 6
                    }
                    findLetter7(all) -> {
                        sum += 7
                    }
                    findLetter8(all) -> {
                        sum += 8
                    }
                    findLetter9(all) -> {
                        sum += 9
                    }
                }
            }
            totalSum += sum.toInt()
            nextLine = reader.readLine()
        }
        print(totalSum, 1016804)

    }

    private fun findLetter1(signals: List<String>): String {
        return signals.find { it.length == 2 } ?: ""
    }

    private fun findLetter2(signals: List<String>): String {
        val index5 = find5(signals)
        return signals.find { it.length == 5 && !it.containsChar(index5) } ?: ""
    }

    private fun findLetter3(signals: List<String>): String {
        val index2 = find2(signals)
        val index5 = find5(signals)
        return signals.find { it.length == 5 && it.containsChar(index2) && it.containsChar(index5) } ?: ""
    }

    private fun findLetter4(signals: List<String>): String {
        return signals.find { it.length == 4 } ?: ""
    }

    private fun findLetter5(signals: List<String>): String {
        val index2 = find2(signals)
        val index5 = find5(signals)
        return signals.find { it.length == 5 && !it.containsChar(index2) && it.containsChar(index5) } ?: ""
    }

    private fun findLetter6(signals: List<String>): String {
        val index2 = find2(signals)
        return signals.find { it.length == 6 && !it.containsChar(index2) } ?: ""
    }

    private fun findLetter7(signals: List<String>): String {
        return signals.find { it.length == 3 } ?: ""
    }

    private fun findLetter8(signals: List<String>): String {
        return signals.find { it.length == 7 } ?: ""
    }

    private fun findLetter9(signals: List<String>): String {
        val index4 = find4(signals)
        return signals.find { it.length == 6 && !it.containsChar(index4) } ?: ""
    }

    private fun findLetter0(signals: List<String>): String {
        val letter9 = findLetter9(signals)
        val letter6 = findLetter6(signals)
        return signals.find { it.length == 6 && it != letter9 && it != letter6 } ?: ""
    }

    private fun String.containsChar(char: Char): Boolean {
        return toCharArray().contains(char)
    }

    private fun find2(signals: List<String>): Char {
        val numberOne = findLetter1(signals)
        signals.forEach { number ->
            if (number.length == 6) {
                numberOne.forEach {
                    if (!number.containsChar(it)) {
                        return it
                    }
                }
            }
        }
        return 'f'
    }

    private fun find4(signals: List<String>): Char {
        val letter5 = findLetter5(signals)
        val letter8 = findLetter8(signals)
        val index2 = find2(signals)
        return letter8.find {
            !letter5.contains(it) && index2 != it
        } ?: 'g'
    }

    private fun find5(signals: List<String>): Char {
        val numberOne = findLetter1(signals)
        val index2 = find2(signals)
        return numberOne.first { it != index2 }
    }
}
