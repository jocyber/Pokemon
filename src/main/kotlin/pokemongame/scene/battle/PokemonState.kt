package pokemongame.scene.battle

import com.lehaine.littlekt.math.Vec2f

data class PokemonState(
    val stats: BattleStats = BattleStats(),
    val position: Vec2f,
    val wasHit: Boolean = false,
)

data class BattleStats(
    val attack: Int = 0,
    val defense: Int = 0,
    val specialAttack: Int = 0,
    val specialDefense: Int = 0,
    val speed: Int = 0,
    val evasion: Int = 0,
)
