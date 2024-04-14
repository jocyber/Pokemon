package pokemongame.scene.battle

import pokemongame.scene.Weather

data class BattleSceneState(
    var playerState: PokemonBattleState,
    var enemyState: PokemonBattleState,
    var turn: BattleEntity = BattleEntity.PLAYER,
    var weather: Weather,
    var battleState: BattleExecutionState = BattleExecutionState.USER_SELECTING,
    var displayState: DisplayState = DisplayState.BATTLE,
    var isSpecialScreenActive: Boolean = false,
    var isPhysicalScreenActive: Boolean = false,
) {
    lateinit var currentTarget: PokemonBattleState
    lateinit var opposingTarget: PokemonBattleState
}

enum class DisplayState {
    BATTLE,
    PARTY,
    BAG,
}

enum class BattleExecutionState {
    USER_SELECTING,
    EXECUTING_BATTLE,
}
