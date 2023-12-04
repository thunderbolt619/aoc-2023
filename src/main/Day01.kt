fun main() {
    fun part1(input: List<String>): Int {
        return sumCalibrationValues(input, hasNumberWords = false)
    }

    fun part2(input: List<String>): Int {
        return sumCalibrationValues(input, hasNumberWords = true)
    }

    val input = readInput("Day01", "input")
    part1(input).println()
    part2(input).println()
}
fun sumCalibrationValues(calibrationDocument: List<String>, hasNumberWords: Boolean): Int {
    var sum = 0
    for (line in calibrationDocument) {
        sum += filterNumbers(
            if (hasNumberWords) {
                findAndReplace(line)
            } else {
                line
            }
        )
    }
    return sum
}

fun filterNumbers(line: String): Int {
    val reg = "\\d".toRegex()
    val firstNumber = line.first { reg.matches(it.toString()) }.digitToInt()
    val secondNumber = line.last { reg.matches(it.toString()) }.digitToInt()
    return "$firstNumber$secondNumber".toInt()
}

fun findAndReplace(line: String): String {
    val first = line.findAnyOf(NumberWord.wordList)
    val firstReplacement = first?.second
    val firstIndex = first?.first
    val second = line.findLastAnyOf(NumberWord.wordList)
    val secondReplacement = second?.second
    val secondIndex = second?.first
    return line
        .replaceRange((firstIndex ?: 0)..(firstIndex ?: 0), NumberWord.wordToNumber[firstReplacement] ?: line.first().toString())
        .replaceRange((secondIndex ?: 0)..(secondIndex ?: 0), NumberWord.wordToNumber[secondReplacement] ?: line.first().toString())
}

object NumberWord {
    val wordToNumber = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    val wordList = wordToNumber.keys.toList()
}
