package pokemongame.pokemon

import pokemongame.pokemon.PokemonType.Companion.HALF_EFFECTIVE
import pokemongame.pokemon.PokemonType.Companion.NORMALLY_EFFECTIVE
import pokemongame.pokemon.PokemonType.FIRE
import pokemongame.pokemon.PokemonType.WATER

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
interface Pokemon {
    val movesByLevel: Map<Int, Lazy<PokemonMove>>
    val type: Pair<PokemonType, PokemonType?>
}

data object Primeape : Pokemon {
    override val type = Pair(PokemonType.FIGHTING, null)
    override val movesByLevel = mapOf(1 to lazy { Tackle })
}

data object Zigzagoon : Pokemon {
    override val type = Pair(PokemonType.NORMAL, null)
    override val movesByLevel = mapOf(1 to lazy { Tackle })
}

// only battles care about this utility. Move extension function to the battle handlers.
fun Pokemon.typeEffectiveness(attackingType: PokemonType): Float {
    fun PokemonType.calculateEffectiveness(): Float =
        when (this) {
            FIRE ->
                when (attackingType) {
                    FIRE,
                    WATER -> HALF_EFFECTIVE
                    else -> NORMALLY_EFFECTIVE
                }
            WATER ->
                when (attackingType) {
                    FIRE,
                    WATER -> HALF_EFFECTIVE
                    else -> NORMALLY_EFFECTIVE
                }
            else -> 1f
        }

    return type.let { (primaryType, secondaryType) ->
        primaryType.calculateEffectiveness() *
            (secondaryType?.let(PokemonType::calculateEffectiveness) ?: 1f)
    }
}
