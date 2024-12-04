package pokemongame.pokemon

import pokemongame.moves.Tackle
import pokemongame.types.PokemonType

data object Zigzagoon : Pokemon {
    override val type = Pair(PokemonType.NORMAL, null)

    override val movesByLevel = mapOf(1 to lazy { Tackle })
    // add growl and tail whip
}
