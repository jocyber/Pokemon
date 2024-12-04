package pokemongame.scene.battle

import pokemongame.pokemon.state.PokemonStats

data class PokemonBattleState(
    val stats: PokemonStats,
    val battleStats: BattleStats = BattleStats(),
    var isAttacking: Boolean = false,
    var wasHit: Boolean = false,
    var didFlinch: Boolean = false,
    var isInvulnerable: Boolean = false,
    var turnPassed: Boolean = false,
    var isFainted: Boolean = false,
    var isBurned: Boolean = false,
)

data class BattleStats(
    var attack: Int = 0,
    var defense: Int = 0,
    var specialAttack: Int = 0,
    var specialDefense: Int = 0,
    var speed: Int = 0,
    var evasion: Int = 0,
)
