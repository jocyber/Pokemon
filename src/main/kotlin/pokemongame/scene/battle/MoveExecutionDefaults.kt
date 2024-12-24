package pokemongame.scene.battle

import pokemongame.pokemon.*
import pokemongame.pokemon.PokemonType.FIRE
import pokemongame.pokemon.PokemonType.WATER

data class MoveExecutionResult(
    val turnsLeft: Int,
    val accuracy: Int?,
    val damageToTarget: Int?,
    val effectiveness: TypeEffectiveness?,
    val landedCritical: Boolean,
    val battleStatChange: BattleStats?,
)

const val CRITICAL_MODIFIER = 1.5f

private val EntityState.isInvulnerable: Boolean
    get() = isInSky || isUnderground || isFainted

fun BattleSceneState.defaultExecuteMove(
    damageToTarget: Int? = null,
    isInvulnerable: Boolean? = null,
    accuracy: Int? = null,
    criticalHitRange: IntRange = 1..24,
    battleStatChange: BattleStats? = null,
): MoveExecutionResult? {
    val chosenMove = currentTarget.chosenMove
    val landedCritical = criticalHitRange.random() == 1

    val effectiveness = opposingTarget.pokemonInstance.typeEffectiveness(chosenMove.type)
    val opposingDamage =
        damageToTarget ?: chosenMove.basePower?.let { it.value * effectiveness.multiplier }?.toInt()

    return when {
        isInvulnerable ?: opposingTarget.isInvulnerable -> null
        else ->
            MoveExecutionResult(
                damageToTarget = opposingDamage,
                turnsLeft = 0,
                accuracy = accuracy ?: currentTarget.chosenMove.accuracy?.value,
                effectiveness = effectiveness,
                landedCritical = landedCritical,
                battleStatChange = battleStatChange,
            )
    }
}

private fun Pokemon.typeEffectiveness(attackingType: PokemonType): TypeEffectiveness {
    fun PokemonType.calculateEffectiveness(): TypeEffectiveness =
        when (this) {
            FIRE ->
                when (attackingType) {
                    WATER -> SuperEffective()
                    else -> NormallyEffective() }
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
