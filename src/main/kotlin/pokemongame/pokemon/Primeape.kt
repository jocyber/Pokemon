package pokemongame.pokemon

import pokemongame.moves.Tackle
import pokemongame.types.PokemonType

data object Primeape : Pokemon {
    override val frontTextureWidth = 66
    override val frontTextureHeight = 60
    override val frontFrames = 56

    override val backTextureWidth = 72
    override val backTextureHeight = 58
    override val backFrames = 50

    override val type = Pair(PokemonType.FIGHTING, null)

    override val movesByLevel = mapOf(1 to Tackle)
}
