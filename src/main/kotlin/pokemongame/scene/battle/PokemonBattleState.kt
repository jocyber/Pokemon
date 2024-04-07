package pokemongame.scene.battle

import com.lehaine.littlekt.graphics.Color
import com.lehaine.littlekt.graphics.toFloatBits
import com.lehaine.littlekt.math.Vec2f
import pokemongame.pokemon.state.PokemonStats
import pokemongame.scene.battle.ui.HealthBar

// TODO: make a test generator for this class that allows the client to override what they want
data class PokemonBattleState(
    val stats: PokemonStats,
    val battleStats: BattleStats = BattleStats(),
    var position: Vec2f,
    var colorBits: Float = Color.WHITE.toFloatBits(),
    var isAttacking: Boolean = false,
    var wasHit: Boolean = false,
    var didFlinch: Boolean = false,
    var isInvulnerable: Boolean = false,
    var turnPassed: Boolean = false,
    var isFainted: Boolean = false,
    var isBurned: Boolean = false,
) {
    lateinit var healthBar: HealthBar
}

data class BattleStats(
    var attack: Int = 0,
    var defense: Int = 0,
    var specialAttack: Int = 0,
    var specialDefense: Int = 0,
    var speed: Int = 0,
    var evasion: Int = 0,
)
