package pokemongame.pokemon

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graphics.Texture
import pokemongame.types.PokemonType

data object Zigzagoon : Pokemon {
    override val textureWidth = 58
    override val textureHeight = 42
    override val frames = 112

    override val movesByLevel = mapOf(1 to "Tackle")
    override val type = Pair(PokemonType.NORMAL, null)

    override suspend fun getTexture(context: Context): Texture =
        context.resourcesVfs["assets/sprites/pokemon/front/zigzagoon.png"].readTexture()
}
