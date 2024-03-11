package pokemongame.scene.battle

import com.lehaine.littlekt.math.Vec2f

data class PokemonBattleState(
    val stats: BattleStats = BattleStats(),
    var position: Vec2f,
    var wasHit: Boolean = false,
    var didFlinch: Boolean = false,
    var isInvulnerable: Boolean = false,
    var turnPassed: Boolean = false,
    var isFainted: Boolean = false,
)

data class BattleStats(
    var attack: Int = 0,
    var defense: Int = 0,
    var specialAttack: Int = 0,
    var specialDefense: Int = 0,
    var speed: Int = 0,
    var evasion: Int = 0,
)
