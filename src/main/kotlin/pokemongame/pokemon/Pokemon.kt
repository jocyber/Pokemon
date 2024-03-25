package pokemongame.pokemon

import pokemongame.moves.PokemonMove
import pokemongame.types.PokemonType

sealed interface Pokemon {
    val frontTextureWidth: Int
    val frontTextureHeight: Int
    val frontFrames: Int

    val backTextureWidth: Int
    val backTextureHeight: Int
    val backFrames: Int

    // based on generation 5 move sets
    val movesByLevel: Map<Int, PokemonMove>
    val type: Pair<PokemonType, PokemonType?>
}
