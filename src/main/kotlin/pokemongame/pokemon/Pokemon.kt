package pokemongame.pokemon

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import pokemongame.moves.PokemonMove
import pokemongame.types.PokemonType

sealed interface Pokemon {
    val textureWidth: Int
    val textureHeight: Int
    val frames: Int

    // based on generation 5 move sets
    val movesByLevel: Map<Int, PokemonMove>
    val type: Pair<PokemonType, PokemonType?>

    suspend fun getTexture(context: Context): Texture
}
