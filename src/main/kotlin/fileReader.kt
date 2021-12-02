import java.io.BufferedReader
import java.io.FileReader

fun <T : Any> T.getReader(): BufferedReader {
    val fileName = this.javaClass.name
    return BufferedReader(FileReader("src/main/kotlin/$fileName/$fileName.txt"))
}


fun BufferedReader.readLn(): String? = readLine() // string line
fun BufferedReader.readInt() = readLn()?.toInt() // single int
fun BufferedReader.readLong() = readLn()?.toLong() // single long
fun BufferedReader.readFloat() = readLn()?.toFloat() // single float

// Read line split by space
fun BufferedReader.readStrings() = readLn()?.split(" ") // list of strings

fun BufferedReader.readInts() = readStrings()?.map { it.toInt() } // list of ints
fun BufferedReader.readLongs() = readStrings()?.map { it.toLong() } // list of longs
fun BufferedReader.readFloats() = readStrings()?.map { it.toFloat() } // list of floats