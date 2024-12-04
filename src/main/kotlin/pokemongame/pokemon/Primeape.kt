package pokemongame.pokemon

import pokemongame.moves.Tackle
import pokemongame.types.PokemonType

data object Primeape : Pokemon {
    override val type = Pair(PokemonType.FIGHTING, null)

    override val movesByLevel = mapOf(1 to lazy { Tackle })
}
