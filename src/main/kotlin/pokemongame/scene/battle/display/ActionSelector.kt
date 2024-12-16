package pokemongame.scene.battle.display

/**
 * Select should be total. It should maintain a list of actions, take in input, and return the newly
 * selected action.
 */
class ActionSelector {
    private val actionsGrid: List<List<String>> =
        listOf(listOf("Battle", "Bag"), listOf("Pokemon", "Run"))
    private var currCoordinates = Pair(0, 0)

    // should only be able to take in WASD keys
    fun select(key: String) {
        val (i, j) = currCoordinates

        return when (key) {
                "W" -> if (i == 1) Pair(0, j) else currCoordinates
                "A" -> if (j == 1) Pair(i, 0) else currCoordinates
                "D" -> if (j == 0) Pair(i, 1) else currCoordinates
                "S" -> if (i == 0) Pair(1, j) else currCoordinates
                else -> TODO("make impossible")
            }
            .also { currCoordinates = it }
            .let { (i, j) -> actionsGrid[i][j] }
    }
}
