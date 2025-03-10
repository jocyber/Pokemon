package pokemongame.pokemon

/**
 * An interface that represents all the information about a particular Pokemon.
 * <ul>
 * <li> All the moves that the Pokemon can learn.
 * <li> The typing of the Pokemon. A Pokemon can either have a singular type or two types.
 * </ul>
 *
 * @see <a href="https://pokemondb.net/move/all">All moves</a>
 * @author Jordan Harman
 */
interface Pokemon {
    val movesByLevel: Map<Int, PokemonMove>
    val type: Pair<PokemonType, PokemonType?>
}

data object Primeape : Pokemon {
    override val type = Pair(PokemonType.FIGHTING, null)
    override val movesByLevel = mapOf(1 to Tackle)
}

data object Zigzagoon : Pokemon {
    override val type = Pair(PokemonType.NORMAL, null)
    override val movesByLevel = mapOf(1 to Tackle)
}
