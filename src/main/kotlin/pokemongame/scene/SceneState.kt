package pokemongame.scene

import pokemongame.scene.battle.BattleAction

abstract class SceneState {
    /**
     * A list of button actions that can be displayed on the screen. The SceneDrawers are
     * responsible for where they're placed on the screen.
     */
    abstract val availableActions: List<BattleAction>

    lateinit var selectedAction: BattleAction
}
