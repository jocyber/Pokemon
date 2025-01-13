package pokemongame.scene.battle

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
