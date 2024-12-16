package pokemongame.scene.battle

import pokemongame.pokemon.Category.STATUS
import pokemongame.pokemon.SuckerPunch
import pokemongame.pokemon.Tackle

data class MoveExecutionResult(
    val damageToTarget: Int,
    val turnsLeft: Int,
    val accuracy: Int,
)

private val EntityState.isInvulnerable: Boolean
    get() = isInSky || isUnderground || isFainted

private fun BattleSceneState.defaultExecuteMove(
    damageToTarget: Int? = null,
    isInvulnerable: Boolean? = null,
    accuracy: Int? = null,
) =
    when {
        isInvulnerable ?: opposingTarget.isInvulnerable -> null
        else ->
            MoveExecutionResult(
                damageToTarget = damageToTarget ?: currentTarget.attack,
                turnsLeft = 0,
                accuracy = accuracy ?: currentTarget.chosenMove.accuracy,
            )
    }

fun Tackle.visitExecuteMove(sceneState: BattleSceneState) = sceneState.defaultExecuteMove()

fun SuckerPunch.visitExecuteMove(sceneState: BattleSceneState): MoveExecutionResult? =
    with(sceneState) {
        if (opposingTarget.alreadyAttacked || opposingTarget.chosenMove.category == STATUS) {
            null
        } else {
            defaultExecuteMove()
        }
    }
