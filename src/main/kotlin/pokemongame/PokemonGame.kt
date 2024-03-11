package pokemongame

import com.google.inject.Guice
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.ContextListener
import com.lehaine.littlekt.createLittleKtApp
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graphics.gl.ClearBufferMask
import pokemongame.guice.BattleUiModule
import pokemongame.pokemon.Zigzagoon
import pokemongame.pokemon.state.Gender
import pokemongame.pokemon.state.PokemonModel
import pokemongame.scene.SCREEN_HEIGHT
import pokemongame.scene.SCREEN_WIDTH
import pokemongame.scene.Weather
import pokemongame.scene.battle.BattleScene
import pokemongame.scene.battle.BattleSceneController

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
    val injector = Guice.createInjector(BattleUiModule(context))

    override suspend fun Context.start() {
        val battleScene =
            BattleScene(
                context,
                backgroundTexture =
                    resourcesVfs["assets/backgrounds/grass_background.png"].readTexture(),
                enemyPokemon =
                    PokemonModel(
                        level = 10,
                        currentHealth = 30,
                        gender = Gender.MALE,
                        pokemon = Zigzagoon
                    ),
                playerPokemon =
                    PokemonModel(
                        level = 10,
                        currentHealth = 30,
                        gender = Gender.MALE,
                        pokemon = Zigzagoon
                    ),
                weather = Weather.SUN,
            )
        val sceneController = BattleSceneController(battleScene)

        onRender { dt ->
            gl.clear(ClearBufferMask.COLOR_BUFFER_BIT)
            sceneController.controlLogic(dt)
        }
    }
}
