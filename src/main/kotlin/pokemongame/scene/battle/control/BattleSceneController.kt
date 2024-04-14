package pokemongame.scene.battle.control

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.input.Key
import kotlin.time.Duration
import pokemongame.scene.battle.BattleExecutionState
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.DisplayState

// the party manager, based on the context to which it's being loaded, it can perform
// any arbitrary action when a pokemon is selected.
class BattleSceneController(
        private val context: Context,
        private val sceneState: BattleSceneState,
        private val battleDisplayDrawer: BattleDisplayDrawer,
        private val battleSelectionDrawer: BattleActionSelector,
) {
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
        battleSelectionDrawer.draw(dt)

        when (sceneState.battleState) {
            BattleExecutionState.USER_SELECTING -> {
                val input = context.input

                when {
                    input.isKeyPressed(Key.E) -> {
                        if (battleDisplayExecutor == null) {
                            battleDisplayExecutor = BattleDisplayExecutor(sceneState)
                            sceneState.battleState = BattleExecutionState.EXECUTING_BATTLE
                        }
                    }
                }
            }
            BattleExecutionState.EXECUTING_BATTLE -> battleDisplayExecutor!!.updateTurn(dt)
        }
    }
}
