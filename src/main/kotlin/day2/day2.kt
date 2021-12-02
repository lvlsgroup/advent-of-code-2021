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
            val movement = split[1]
            when (instruction) {
                "forward" -> horizontal += movement.toInt()
                "down" -> vertical += movement.toInt()
                "up" -> vertical -= movement.toInt()
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
            val movement = split[1]
            when (instruction) {
                "forward" -> {
                    horizontal += movement.toInt()
                    vertical += movement.toInt() * aim
                }
                "down" -> aim += movement.toInt()
                "up" -> aim -= movement.toInt()
            }
            instructionAndMovement = reader.readLn()
        }
        println(horizontal * vertical)
    }
}