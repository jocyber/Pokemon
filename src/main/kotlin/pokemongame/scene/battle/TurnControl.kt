package pokemongame.scene.battle

/**
 * The main driver for controlling a battle scene which gives all the data about a turn execution.
 */
fun controlTurn(sceneState: BattleSceneState) {
    val (first, second) = sceneState.turnOrder()
}

private fun BattleSceneState.turnOrder(): Pair<EntityState, EntityState> {
    fun EntityState.speed(): Int = speed

    val playerFirst = Pair(player, enemy)
    val enemyFirst = Pair(enemy, player)

    return when {
        player.chosenMove.priority > enemy.chosenMove.priority -> playerFirst
        player.chosenMove.priority < enemy.chosenMove.priority -> enemyFirst
        else ->
            when {
                player.speed() > enemy.speed() -> playerFirst
                player.speed() < enemy.speed() -> enemyFirst
                else -> if ((0..1).random() == 0) playerFirst else enemyFirst
            }
    }
}
