package pokemongame

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.ContextListener
import com.lehaine.littlekt.createLittleKtApp
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graphics.gl.ClearBufferMask
import pokemongame.pokemon.Zigzagoon
import pokemongame.scene.BattleScene
import pokemongame.scene.BattleSceneController
import pokemongame.scene.SCREEN_HEIGHT
import pokemongame.scene.SCREEN_WIDTH

object PokemonGame {
    @JvmStatic
    fun main(args: Array<String>) {
        val app =
            createLittleKtApp {
                    width = SCREEN_WIDTH.toInt()
                    height = SCREEN_HEIGHT.toInt()
                    vSync = true
                    resizeable = true
                    title = "Pokemon"
                }
                .start { GameCore(it) }
    }
}

class GameCore(context: Context) : ContextListener(context) {
    override suspend fun Context.start() {
        val battleScene =
            BattleScene(
                context,
                backgroundTexture =
                    resourcesVfs["assets/backgrounds/grass_background.png"].readTexture(),
                enemyPokemon = Zigzagoon,
                playerPokemon = Zigzagoon
            )
        val sceneController = BattleSceneController(battleScene)

        onRender { dt ->
            gl.clear(ClearBufferMask.COLOR_BUFFER_BIT)
            sceneController.controlLogic(dt)
        }
    }
}
