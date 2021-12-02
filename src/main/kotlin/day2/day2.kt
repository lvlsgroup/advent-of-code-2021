fun main() {
    val day = Day2()
    day.a()
    day.b()
}

class Day2 {
    fun main() {
        a()
        b()
    }

    fun a() {
        val reader = getReader("day2")
        var horizontal = 0
        var vertical = 0
        var instructionAndMovement = reader.readLn()
        while (instructionAndMovement != null) {
            val split = instructionAndMovement.split(" ")
            val instruction = split[0]
            val action = split[1]
            when (instruction) {
                "forward" -> horizontal += action.toInt()
                "down" -> vertical += action.toInt()
                "up" -> vertical -= action.toInt()
            }
            instructionAndMovement = reader.readLn()
        }
        println(horizontal * vertical)
    }

    fun b() {
        val reader = getReader("day2")
        var horizontal = 0
        var vertical = 0
        var aim = 0
        var instructionAndMovement = reader.readLn()
        while (instructionAndMovement != null) {
            val split = instructionAndMovement.split(" ")
            val instruction = split[0]
            val action = split[1]
            when (instruction) {
                "forward" -> {
                    horizontal += action.toInt()
                    vertical += action.toInt() * aim
                }
                "down" -> aim += action.toInt()
                "up" -> aim -= action.toInt()
            }
            instructionAndMovement = reader.readLn()
        }
        println(horizontal * vertical)
    }
}