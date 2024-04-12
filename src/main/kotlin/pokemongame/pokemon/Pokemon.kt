package pokemongame.pokemon

import pokemongame.moves.PokemonMove
import pokemongame.types.PokemonType

/**
 * An interface that represents all the information about a particular Pokemon.
 * <ul>
 * <li> Front and back texture widths and frames for the {@link BattleSceneDrawer} to use.
 * <li> All the moves that the Pokemon can learn. The initialization of each move is lazy as to not
 *   call all their constructors once a Pokemon is first initialized.
 * <li> The typing of the Pokemon. A Pokemon can either have a singular type and two types.
 * </ul>
 *
 * @author Jordan Harman
 * @see <a href="https://pokemondb.net/move/all">All moves</a>
 */
sealed interface Pokemon {
    val frontTextureWidth: Int
    val frontTextureHeight: Int
    val frontFrames: Int

    val backTextureWidth: Int
    val backTextureHeight: Int
    val backFrames: Int

    val movesByLevel: Map<Int, Lazy<PokemonMove>>
    val type: Pair<PokemonType, PokemonType?>
}
