import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        return solve(input, powerMode = false)
    }

    fun part2(input: List<String>): Int {
        return solve(input, powerMode = true)
    }

    val input = readInput("Day02", "input")
    part1(input).println()
    part2(input).println()
}

fun solve(games: List<String>, powerMode: Boolean): Int {
    var sum = 0
    for (line in games) {
        val id = getGameId(line)
        val gameList = line
            .substringAfter(": ")
            .split("; ")
        val colorMap = mapMaxColor(gameList)

        if (powerMode) {
            sum += getColorPowers(colorMap)
        } else if (isValidGame(colorMap)) {
            sum += id
        }
    }
    return sum
}

fun isValidGame(colorMap: Map<String, Int>): Boolean {
    val invalidEntries = colorMap.entries
        .find {
            it.value > (Cubes.colorMax[it.key] ?: Int.MAX_VALUE)
        }
    return invalidEntries == null
}

fun getColorPowers(colorMap: Map<String, Int>): Int {
    var p = 1
    colorMap.forEach { p *= it.value }
    return p
}

fun getGameId(line: String): Int {
    val reg = "\\d".toRegex()
    return line.substringBefore(':').filter { reg.matches(it.toString()) }.toInt()
}

fun mapMaxColor(colorList: List<String>): Map<String, Int> {
    val colorMap = mutableMapOf<String, Int>()
    colorList.map {
        it.split(", ").map { i ->
            val k = i.substringAfterLast(" ")
            val v = i.substringBefore(" ").toInt()
            colorMap[k] = max(v, colorMap[k] ?: 0)
        }
    }
    return colorMap
}

object Cubes {
    val colorMax = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
}