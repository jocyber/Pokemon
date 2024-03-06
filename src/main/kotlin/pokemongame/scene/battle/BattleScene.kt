package pokemongame.scene.battle

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import pokemongame.pokemon.Pokemon

// use guice for singleton textures
data class BattleScene(
    val context: Context,
    val backgroundTexture: Texture,
    val enemyPokemon: Pokemon,
    val playerPokemon: Pokemon,
)
