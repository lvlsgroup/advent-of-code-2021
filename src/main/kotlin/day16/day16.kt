fun main() {
    Day16()
}

class Day16 : Runner() {

    override fun a() {

        val reader = getReader()
        val nextLine = reader.readLine()
        val binary = hexToBinary(nextLine)
        val (packet, _) = solve(binary)

        println(packet.sumVersions())
    }

    override fun b() {
        val reader = getReader()
        val nextLine = reader.readLine()
        val binary = hexToBinary(nextLine)
        val (packet, _) = solve(binary)

        println(packet.evaluate())
    }

    sealed class Packet(
        val version: Int,
        val typeId: Int
    ) {
        open fun sumVersions() = version
        abstract fun evaluate(): Long
    }

    class Literal(
        version: Int,
        private val number: Long
    ) : Packet(version, 4) {

        override fun evaluate() = number
    }

    class Operator(
        version: Int,
        typeId: Int,
        private val subPackets: List<Packet>
    ) : Packet(version, typeId) {

        override fun sumVersions(): Int {
            return version + subPackets.sumOf { it.sumVersions() }
        }

        override fun evaluate(): Long {
            return when (typeId) {
                0 -> subPackets.sumOf { it.evaluate() }
                1 -> subPackets.fold(1L) { prod, sp -> prod * sp.evaluate() }
                2 -> subPackets.minOfOrNull { it.evaluate() } ?: 0L
                3 -> subPackets.maxOfOrNull { it.evaluate() } ?: 0L
                5 -> {
                    val (a, b) = subPackets.map { it.evaluate() }
                    if (a > b) 1L else 0L
                }
                6 -> {
                    val (a, b) = subPackets.map { it.evaluate() }
                    if (a < b) 1L else 0L
                }
                7 -> {
                    val (a, b) = subPackets.map { it.evaluate() }
                    if (a == b) 1L else 0L
                }
                else -> throw RuntimeException("Illegal type ID $typeId")
            }
        }
    }

    data class PacketParseResult(
        val packet: Packet,
        val parsedLength: Int
    )

    data class LiteralParseResult(
        val number: Long,
        val parsedLength: Int
    )

    data class OperatorParseResult(
        val subPackets: List<Packet>,
        val parsedLength: Int
    )

    fun solve(binary: String): PacketParseResult {

        val version = binary.substring(0, 3).binaryToInt()
        val typeId = binary.substring(3, 6).binaryToInt()
        return if (typeId == 4) {
            val result = solveLiteral(binary.substring(6))
            PacketParseResult(Literal(version, result.number), result.parsedLength + 6)
        } else {
            val result = solveOperator(binary.substring(6))
            PacketParseResult(Operator(version, typeId, result.subPackets), result.parsedLength + 6)
        }
    }

    fun solveOperator(binary: String): OperatorParseResult {
        val lengthType = binary[0]
        val subPackets = mutableListOf<Packet>()
        var parsedLength = 1

        if (lengthType == '0') {

            val totalLenBits = binary.substring(1..15).binaryToInt()
            parsedLength += 15 + totalLenBits
            var subsLen = 0

            while (subsLen < totalLenBits) {
                val (subPacket, len) = solve(binary.substring(16 + subsLen))
                subPackets.add(subPacket)
                subsLen += len
            }

        } else {
            val numSubPackets = binary.substring(1..11).binaryToInt()
            parsedLength += 11
            var subsLen = 0

            repeat(numSubPackets) {
                val (subPacket, len) = solve(binary.substring(12 + subsLen))
                subPackets.add(subPacket)
                subsLen += len
            }

            parsedLength += subsLen
        }

        return OperatorParseResult(subPackets, parsedLength)
    }

    fun solveLiteral(binary: String): LiteralParseResult {
        var offset = 0
        var dataStr = ""
        while (offset + 5 <= binary.length) {
            val segment = binary.substring(offset until offset + 5)
            dataStr += segment.substring(1)
            offset += 5
            if (segment[0] == '0') {
                break
            }
        }
        val number = dataStr.binaryToLong()
        return LiteralParseResult(number, offset)
    }

    fun String.binaryToLong() = toLong(2)

    fun String.binaryToInt() = toInt(2)

    fun hexToBinary(hex: String): String {
        var char = ""
        hex.forEach {
            char += when (it.toString()) {
                "0" -> "0000"
                "1" -> "0001"
                "2" -> "0010"
                "3" -> "0011"
                "4" -> "0100"
                "5" -> "0101"
                "6" -> "0110"
                "7" -> "0111"
                "8" -> "1000"
                "9" -> "1001"
                "A" -> "1010"
                "B" -> "1011"
                "C" -> "1100"
                "D" -> "1101"
                "E" -> "1110"
                "F" -> "1111"
                else -> "0"
            }
        }
        return char
    }
}


