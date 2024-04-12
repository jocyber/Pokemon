package pokemongame.scene.battle.control

import com.lehaine.littlekt.input.Key
import kotlin.time.Duration
import pokemongame.scene.SceneDrawer
import pokemongame.scene.battle.BattleScene

class BattleSceneController(private val battleScene: BattleScene) {
    private enum class BATTLE_STATE {
        BATTLE_SCENE,
    }

    // have a move selector and bag handler
    private var turnExecutor: TurnExecutor? = null

    private val currentState = BATTLE_STATE.BATTLE_SCENE

    private val sceneDrawerByInput =
        mapOf<BATTLE_STATE, SceneDrawer>(
            BATTLE_STATE.BATTLE_SCENE to BattleSceneDrawer(battleScene)
        )

    fun controlLogic(dt: Duration) {
        if (battleScene.context.input.isKeyPressed(Key.E) && turnExecutor == null) {
            turnExecutor = TurnExecutor(battleScene)
        }

        //        if (turnExecutor?.updateTurn(dt) == true) {
        //            turnExecutor = null
        //        }

        sceneDrawerByInput[currentState]!!.draw(dt)
        turnExecutor?.updateTurn(dt)
    }
}
