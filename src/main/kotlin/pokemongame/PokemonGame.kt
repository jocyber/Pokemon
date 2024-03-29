package pokemongame

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.ContextListener
import com.lehaine.littlekt.createLittleKtApp
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graphics.gl.ClearBufferMask
import org.koin.core.context.startKoin
import pokemongame.koin.animationTexturesModule
import pokemongame.koin.animationsModule
import pokemongame.koin.audioClipsModule
import pokemongame.koin.audioStreamsModule
import pokemongame.koin.battleSceneModule
import pokemongame.koin.pokemonTexturesModule
import pokemongame.pokemon.Primeape
import pokemongame.pokemon.Zigzagoon
import pokemongame.pokemon.state.Gender
import pokemongame.pokemon.state.PokemonModel
import pokemongame.scene.SCREEN_HEIGHT
import pokemongame.scene.SCREEN_WIDTH
import pokemongame.scene.Weather
import pokemongame.scene.battle.BattleScene
import pokemongame.scene.battle.control.BattleSceneController

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
        startKoin {
            modules(
                battleSceneModule(context),
                pokemonTexturesModule(context),
                audioStreamsModule(context),
                audioClipsModule(context),
                animationTexturesModule(context),
                animationsModule(context),
            )
        }

        val battleScene =
            BattleScene(
                context,
                backgroundTexture =
                    resourcesVfs["assets/backgrounds/grass_background.png"].readTexture(),
                enemyPokemon =
                    PokemonModel(
                        level = 10,
                        currentHealth = 1,
                        gender = Gender.MALE,
                        pokemon = Zigzagoon
                    ),
                playerPokemon =
                    PokemonModel(
                        level = 10,
                        currentHealth = 1,
                        gender = Gender.MALE,
                        pokemon = Primeape,
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
