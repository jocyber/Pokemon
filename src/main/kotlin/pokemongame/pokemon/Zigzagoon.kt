package pokemongame.pokemon

import pokemongame.moves.Tackle
import pokemongame.types.PokemonType

data object Zigzagoon : Pokemon {
    override val frontTextureWidth = 58
    override val frontTextureHeight = 42
    override val frontFrames = 112

    override val backTextureWidth = 0
    override val backTextureHeight = 0
    override val backFrames = 0

    override val type = Pair(PokemonType.NORMAL, null)

    override val movesByLevel = mapOf(1 to lazy { Tackle })
    // add growl and tail whip
}
