package pokemongame.scene.battle

import pokemongame.pokemon.*
import pokemongame.pokemon.PokemonType.FIRE
import pokemongame.pokemon.PokemonType.WATER

sealed interface MoveExecutionResult {
    data class NormalAttack(
        val targetDamage: Int,
        val effectiveness: TypeEffectiveness,
        val accuracy: Int?,
    ) : MoveExecutionResult

    data class StatusMove(
        val battleStatChange: BattleStats?,
        val target: EntityState,
    ) : MoveExecutionResult

    data object InvalidBasePowerConfiguration : MoveExecutionResult

    data object OneHitKnockOut : MoveExecutionResult

    data object Missed : MoveExecutionResult

    data object Failed : MoveExecutionResult
}

private val EntityState.isInvulnerable: Boolean
    get() = isInSky || isUnderground

/**
 * A pure function that returns information about a move execution. Randomness should be conducted
 * elsewhere to avoid violating the purity of this operation.
 */
fun BattleSceneState.defaultExecuteMove(
    damageToTarget: Int? = null,
    isInvulnerable: Boolean? = null,
    accuracy: Int? = null,
    battleStatChange: BattleStats? = null,
    target: EntityState = opposingTarget,
): MoveExecutionResult {
    val chosenMove = currentTarget.chosenMove

    return when {
        chosenMove.category == Category.STATUS ->
            if (target === opposingTarget && target.isFainted) MoveExecutionResult.Failed
            else MoveExecutionResult.StatusMove(battleStatChange, target)
        target.isFainted -> MoveExecutionResult.Failed
        isInvulnerable ?: target.isInvulnerable -> MoveExecutionResult.Missed
        else -> {
            val effectiveness = target.pokemonInstance.typeEffectiveness(chosenMove.type)
            val opposingDamage =
                damageToTarget
                    ?: chosenMove.basePower?.let { it.value * effectiveness.multiplier }?.toInt()
                    ?: return MoveExecutionResult.InvalidBasePowerConfiguration

            MoveExecutionResult.NormalAttack(
                targetDamage = opposingDamage,
                effectiveness = effectiveness,
                accuracy = accuracy ?: currentTarget.chosenMove.accuracy?.value,
            )
        }
    }
}

private fun Pokemon.typeEffectiveness(attackingType: PokemonType): TypeEffectiveness {
    fun PokemonType.calculateEffectiveness(): TypeEffectiveness =
        when (this) {
            FIRE ->
                when (attackingType) {
                    WATER -> SuperEffective()
                    else -> NormallyEffective()
                }
            WATER ->
                when (attackingType) {
                    FIRE,
                    WATER -> NotVeryEffective()
                    else -> NormallyEffective()
                }
            else -> NormallyEffective()
        }

    return type.let { (primaryType, secondaryType) ->
        primaryType.calculateEffectiveness() *
            (secondaryType?.calculateEffectiveness() ?: NormallyEffective())
    }
}
