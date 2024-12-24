package pokemongame.scene.battle

import pokemongame.pokemon.PokemonMove
import pokemongame.pokemon.state.PokemonInstance
import pokemongame.scene.Weather

// copies will need to be stored so moves that rely on previous actions can work
data class BattleSceneState(
    val player: EntityState,
    val enemy: EntityState,
    val weather: Weather,
    val isSpecialScreenActive: Boolean = false,
    val isPhysicalScreenActive: Boolean = false,
) {
    lateinit var currentTarget: EntityState
    lateinit var opposingTarget: EntityState
}

data class EntityState(
    val pokemonInstance: PokemonInstance,
    val chosenMove: PokemonMove,
    val turns: Int,
    private val battleStats: BattleStats = BattleStats(),
    val storedDamage: Int = 0,
    val wasHit: Boolean = false,
    val alreadyAttacked: Boolean = false,
    val didFlinch: Boolean = false,
    val isInSky: Boolean = false,
    val isUnderground: Boolean = false,
    val isFainted: Boolean = false,
    val isBurned: Boolean = false,
    val isFrozen: Boolean = false,
    val isParalyzed: Boolean = false,
    val isPoisoned: Boolean = false,
) {
    val attack = battleStats.attack.value
    val defense = battleStats.defense.value
    val specialAttack = battleStats.specialAttack.value
    val specialDefense = battleStats.specialDefense.value
    val speed = battleStats.speed.value
    val evasion = battleStats.evasion.value
}

class BattleStats(
    val attack: BattleStat = BattleStat.zero,
    val defense: BattleStat = BattleStat.zero,
    val specialAttack: BattleStat = BattleStat.zero,
    val specialDefense: BattleStat = BattleStat.zero,
    val speed: BattleStat = BattleStat.zero,
    val evasion: BattleStat = BattleStat.zero,
)

/** An integer that must be between -6 and 6 which represents stat increases mid-battle. */
@JvmInline
value class BattleStat private constructor(val value: Int) {
    companion object {
        operator fun invoke(value: Int) =
            when (value) {
                in -6..6 -> BattleStat(value)
                else -> null
            }

        val zero = BattleStat(0)
        val one = BattleStat(1)
        val two = BattleStat(2)
        val three = BattleStat(3)
        val four = BattleStat(4)
        val five = BattleStat(5)
        val six = BattleStat(6)

        val minusOne = BattleStat(-1)
        val minusTwo = BattleStat(-2)
        val minusThree = BattleStat(-3)
        val minusFour = BattleStat(-4)
        val minusFive = BattleStat(-5)
        val minusSix = BattleStat(-6)
    }

    operator fun plus(rhs: Int) = invoke(value + rhs)

    operator fun Int.plus(rhs: BattleStat) = rhs + this
}
