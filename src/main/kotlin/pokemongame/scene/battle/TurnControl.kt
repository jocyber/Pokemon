package pokemongame.scene.battle

import pokemongame.pokemon.TypeEffectiveness

sealed interface TurnControlResult {
    data class NormalAttack(
        val damage: Int?,
        val effectiveness: TypeEffectiveness?,
    ) : TurnControlResult

    data class AttackWithCritical(
        val damage: Int?,
        val effectiveness: TypeEffectiveness?,
    ) : TurnControlResult

    data object OneHitKnockOut : TurnControlResult

    data object Missed : TurnControlResult

    data object Failed : TurnControlResult
}

/**
 * The main driver for controlling a battle scene which gives all the data about a turn execution.
 */
fun BattleSceneState.controlTurn(entity: EntityState): TurnControlResult {
    val moveExecutionResult = entity.chosenMove.execute(this) ?: return TurnControlResult.Failed

    return with(moveExecutionResult) {
        if ((1..100).random() > accuracy) {
            TurnControlResult.Missed
        }

        if (landedCritical) {
            TurnControlResult.AttackWithCritical(
                damage = damageToTarget,
                effectiveness = effectiveness,
            )
        } else {
            TurnControlResult.NormalAttack(
                damage = damageToTarget,
                effectiveness = effectiveness,
            )
        }
    }
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
