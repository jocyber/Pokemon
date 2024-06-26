package pokemongame

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.ContextListener
import com.lehaine.littlekt.createLittleKtApp
import com.lehaine.littlekt.graphics.gl.ClearBufferMask
import pokemongame.koin.startKoinApp
import pokemongame.scene.battle.SCREEN_HEIGHT
import pokemongame.scene.battle.SCREEN_WIDTH
import pokemongame.scene.battle.control.BattleSceneController
import pokemongame.scene.battle.buildBattleSceneState

object PokemonGame {
    @JvmStatic
    fun main(args: Array<String>) {
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
        startKoinApp(context)

        val battleSceneState = buildBattleSceneState()

        val sceneController =
            BattleSceneController(
                context,
                sceneState = battleSceneState,
            )

        onRender { dt ->
            gl.clear(ClearBufferMask.COLOR_BUFFER_BIT)
            sceneController.controlLogic(dt)
        }
    }
}
