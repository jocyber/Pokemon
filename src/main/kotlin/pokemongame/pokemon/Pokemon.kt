package pokemongame.pokemon

import pokemongame.moves.PokemonMove
import pokemongame.types.PokemonType
import pokemongame.types.PokemonType.Companion.HALF_EFFECTIVE
import pokemongame.types.PokemonType.Companion.NORMALLLY_EFFECTIVE
import pokemongame.types.PokemonType.FIRE
import pokemongame.types.PokemonType.WATER

/**
 * An interface that represents all the information about a particular Pokemon.
 * <ul>
 * <li> All the moves that the Pokemon can learn. The initialization of each move is lazy as to not
 *   call all their constructors once a Pokemon is first initialized.
 * <li> The typing of the Pokemon. A Pokemon can either have a singular type or two types.
 * </ul>
 *
 * @see <a href="https://pokemondb.net/move/all">All moves</a>
 * @author Jordan Harman
 */
sealed interface Pokemon {
    val movesByLevel: Map<Int, Lazy<PokemonMove>>
    val type: Pair<PokemonType, PokemonType?>
}

// only battles care about this utility. Move extension function to the battle handlers.
fun Pokemon.typeEffectiveness(attackingType: PokemonType): Float {
    fun PokemonType.calculateEffectiveness(): Float =
        when (this) {
            FIRE ->
                when (attackingType) {
                    FIRE,
                    WATER -> HALF_EFFECTIVE
                    else -> NORMALLLY_EFFECTIVE
                }
            WATER ->
                when (attackingType) {
                    FIRE,
                    WATER -> HALF_EFFECTIVE
                    else -> NORMALLLY_EFFECTIVE
                }
            else -> 1f
        }

    return type.first.calculateEffectiveness() *
        (type.second?.let { it.calculateEffectiveness() } ?: 1f)
}
