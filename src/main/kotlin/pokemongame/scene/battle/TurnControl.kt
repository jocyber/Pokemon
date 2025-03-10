package pokemongame.scene.battle

fun turnOrderComparator(): Comparator<EntityState> =
    compareBy(
        { it.chosenMove.priority },
        { it.speed },
        { (0..1).random() },
    )

fun executeTurnOrder(battleSceneState: BattleSceneState): Sequence<MoveExecutionResult> =
    battleSceneState
        .run { sequenceOf(playerState, enemyState) }
        .sortedWith(turnOrderComparator())
        .map { it.chosenMove.execute(battleSceneState) }
