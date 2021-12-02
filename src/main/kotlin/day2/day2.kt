fun main() {
    Day2()
}

class Day2 : Runner() {
    override fun a() {
        val reader = getReader()
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
        print(horizontal * vertical, 1714680)
    }

    override fun b() {
        val reader = getReader()
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
        print(horizontal * vertical, 1963088820)
    }
}