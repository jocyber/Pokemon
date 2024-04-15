package pokemongame.scene.battle.control

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.input.Key
import kotlinx.coroutines.runBlocking
import pokemongame.scene.battle.BattleAction
import kotlin.time.Duration
import pokemongame.scene.battle.BattleExecutionState
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.DisplayState

// the party manager, based on the context to which it's being loaded, it can perform
// any arbitrary action when a pokemon is selected.
class BattleSceneController(
    private val context: Context,
    private val sceneState: BattleSceneState,
) {
    private val battleDisplayDrawer = BattleDisplayDrawer(
        sceneState,
        runBlocking { context.resourcesVfs["assets/backgrounds/grass_background.png"].readTexture() }
    )
    private val battleSelectionDrawer = BattleActionSelector(sceneState, context)
    private val moveSelector = MoveSelector(sceneState)

    private var battleDisplayExecutor: BattleDisplayExecutor? = null

    fun controlLogic(dt: Duration) {
        when (sceneState.displayState) {
            DisplayState.BATTLE -> controlBattle(dt)
            DisplayState.BAG -> {}
            DisplayState.PARTY -> {}
        }
    }

    private fun controlBattle(dt: Duration) {
        battleDisplayDrawer.draw(dt)

        when (sceneState.battleState) {
            BattleExecutionState.USER_SELECTING_ACTION -> {
                battleSelectionDrawer.draw(dt)

                val input = context.input

                when {
                    input.isKeyPressed(Key.E) -> {
                        if (sceneState.selectedAction == BattleAction.FIGHT) {
                            sceneState.battleState = BattleExecutionState.USER_SELECTING_MOVE
                        }
                    }
                }
            }
            BattleExecutionState.USER_SELECTING_MOVE -> {
                moveSelector.draw(dt)

                if (context.input.isKeyPressed(Key.F)) {
                    sceneState.battleState = BattleExecutionState.USER_SELECTING_ACTION
                }
            }
            BattleExecutionState.EXECUTING_BATTLE -> battleDisplayExecutor!!.updateTurn(dt)
        }
    }
}
