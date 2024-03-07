package pokemongame.scene.battle

import pokemongame.scene.Weather

data class BattleSceneState(
    val playerState: PokemonState,
    val enemyState: PokemonState,
    val weather: Weather,
    val currentTurn: Turn,
    val isSpecialScreenActive: Boolean = false,
    val isPhysicalScreenActive: Boolean = false,
)
