package pokemongame.scene.battle

import pokemongame.scene.Weather

data class BattleSceneState(
    var playerState: PokemonBattleState,
    var enemyState: PokemonBattleState,
    var turn: BattleEntity = BattleEntity.PLAYER,
    var weather: Weather,
    var isSpecialScreenActive: Boolean = false,
    var isPhysicalScreenActive: Boolean = false,
)
