package pokemongame.scene.battle

data class PokemonState(
    val stats: BattleStats,
    val wasHit: Boolean,
)

data class BattleStats(
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val evasion: Int,
)
