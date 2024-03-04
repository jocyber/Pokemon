package pokemongame.pokemon

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import pokemongame.types.PokemonType

sealed interface Pokemon {
    val textureWidth: Int
    val textureHeight: Int
    val frames: Int

    val movesByLevel: Map<Int, String>
    val type: Pair<PokemonType, PokemonType?>

    suspend fun getTexture(context: Context): Texture
}
