package pokemongame.scene.battle

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.math.Vec2f
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.BATTLE_UI_BACKGROUND
import pokemongame.koin.HEALTH_BAR_TEXTURE
import pokemongame.pokemon.state.PokemonModel
import pokemongame.scene.Weather
import pokemongame.scene.battle.ui.HealthBar

class BattleScene(
    val context: Context,
    val backgroundTexture: Texture,
    val enemyPokemon: PokemonModel,
    val playerPokemon: PokemonModel,
    weather: Weather,
) {
    // inject battleUIHandler
    val moveSelectBackground: Texture by inject(Texture::class.java, named(BATTLE_UI_BACKGROUND))

    lateinit var enemyHealthBar: HealthBar
        private set

    lateinit var playerHealthBar: HealthBar
        private set

    lateinit var enemyPokemonTexture: Texture
        private set

    lateinit var playerPokemonTexture: Texture
        private set

    private val texture: Texture by
        inject(Texture::class.java, qualifier = named(HEALTH_BAR_TEXTURE))

    val sceneState =
        BattleSceneState(
            playerState =
                PokemonBattleState(
                    position = PLAYER_POS,
                    currentHealth = playerPokemon.currentHealth,
                ),
            enemyState =
                PokemonBattleState(
                    position = ENEMY_POS,
                    currentHealth = enemyPokemon.currentHealth,
                ),
            weather,
        )

    init {
        setEnemyPokemon(enemyPokemon)
        setPlayerPokemon(playerPokemon)
    }

    fun setEnemyPokemon(pokemonModel: PokemonModel) {
        enemyHealthBar =
            HealthBar(
                currentHealth = enemyPokemon.currentHealth,
                totalHealth = enemyPokemon.currentHealth,
                battleEntity = BattleEntity.ENEMY,
            )

        enemyPokemonTexture = get(Texture::class.java, named(enemyPokemon.pokemon.toString()))
    }

    fun setPlayerPokemon(pokemonModel: PokemonModel) {
        playerHealthBar =
            HealthBar(
                currentHealth = playerPokemon.currentHealth,
                totalHealth = playerPokemon.currentHealth,
                battleEntity = BattleEntity.PLAYER,
            )

        playerPokemonTexture = get(Texture::class.java, named(playerPokemon.pokemon.toString()))
    }

    companion object {
        val ENEMY_POS = Vec2f(650f, 30f)
        val PLAYER_POS = Vec2f(125f, -200f)
    }
}

data class BattleSceneState(
    var playerState: PokemonBattleState,
    var enemyState: PokemonBattleState,
    var weather: Weather,
    var isSpecialScreenActive: Boolean = false,
    var isPhysicalScreenActive: Boolean = false,
)
