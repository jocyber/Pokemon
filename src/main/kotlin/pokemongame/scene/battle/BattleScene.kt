package pokemongame.scene.battle

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.math.Vec2f
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.BATTLE_UI_BACKGROUND
import pokemongame.pokemon.state.PokemonStats
import pokemongame.scene.Weather
import pokemongame.scene.battle.ui.HealthBar

class BattleScene(
    val context: Context,
    val backgroundTexture: Texture,
    val enemyStats: PokemonStats,
    val playerStats: PokemonStats,
    weather: Weather,
) {
    // inject battleUIHandler
    val moveSelectBackground: Texture by inject(Texture::class.java, named(BATTLE_UI_BACKGROUND))

    lateinit var enemyPokemonTexture: Texture
        private set

    lateinit var playerPokemonTexture: Texture
        private set

    val sceneState =
        BattleSceneState(
                playerState =
                    PokemonBattleState(
                        stats = playerStats,
                        position = PLAYER_POS,
                    ),
                enemyState =
                    PokemonBattleState(
                        stats = enemyStats,
                        position = ENEMY_POS,
                    ),
                weather = weather,
                turn = BattleEntity.PLAYER,
            )
            .apply {
                currentTarget = playerState
                opposingTarget = enemyState
            }

    init {
        setEnemyPokemon(enemyStats)
        setPlayerPokemon(playerStats)
    }

    fun setEnemyPokemon(ignored: PokemonStats) {
        sceneState.enemyState.healthBar =
            HealthBar(
                currentHealth = enemyStats.currentHealth,
                totalHealth = enemyStats.currentHealth,
                battleEntity = BattleEntity.ENEMY,
            )

        enemyPokemonTexture = get(Texture::class.java, named(enemyStats.pokemon.toString()))
    }

    fun setPlayerPokemon(ignored: PokemonStats) {
        sceneState.playerState.healthBar =
            HealthBar(
                currentHealth = playerStats.currentHealth,
                totalHealth = playerStats.currentHealth,
                battleEntity = BattleEntity.PLAYER,
            )

        playerPokemonTexture = get(Texture::class.java, named(playerStats.pokemon.toString()))
    }

    companion object {
        val ENEMY_POS = Vec2f(650f, 30f)
        val PLAYER_POS = Vec2f(125f, -200f)
    }
}

data class BattleSceneState(
    var playerState: PokemonBattleState,
    var enemyState: PokemonBattleState,
    var turn: BattleEntity,
    var weather: Weather,
    var isSpecialScreenActive: Boolean = false,
    var isPhysicalScreenActive: Boolean = false,
) {
    lateinit var currentTarget: PokemonBattleState
    lateinit var opposingTarget: PokemonBattleState
}
