package pokemongame.scene.battle

import com.google.inject.Injector
import com.google.inject.Key
import com.google.inject.name.Names
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import pokemongame.guice.ENEMY_HEALTH_BAR_TEXTURE
import pokemongame.guice.PLAYER_HEALTH_BAR_TEXTURE
import pokemongame.pokemon.state.PokemonModel
import pokemongame.scene.Weather
import pokemongame.scene.battle.ui.HealthBar

class BattleScene(
    val context: Context,
    val backgroundTexture: Texture,
    val enemyPokemon: PokemonModel,
    val playerPokemon: PokemonModel,
    weather: Weather,
    injector: Injector,
) {
    var enemyHealthBar: HealthBar? = null
    var playerHealthBar: HealthBar? = null

    private val enemyHealthBarTexture =
        injector.getInstance(Key.get(Texture::class.java, Names.named(ENEMY_HEALTH_BAR_TEXTURE)))
    private val playerHealthBarTexture =
        injector.getInstance(Key.get(Texture::class.java, Names.named(PLAYER_HEALTH_BAR_TEXTURE)))

    init {
        setEnemyPokemon(enemyPokemon)
        setPlayerPokemon(playerPokemon)
    }

    fun setEnemyPokemon(pokemonModel: PokemonModel) {
        enemyHealthBar =
            HealthBar(
                currentHealth = 5,
                totalHealth = pokemonModel.currentHealth,
                isPlayer = false,
                texture = enemyHealthBarTexture,
            )
    }

    fun setPlayerPokemon(pokemonModel: PokemonModel) {
        playerHealthBar =
            HealthBar(
                currentHealth = 20,
                totalHealth = pokemonModel.currentHealth,
                isPlayer = true,
                texture = playerHealthBarTexture,
            )
    }
}

data class BattleSceneState(
    var playerState: PokemonBattleState,
    var enemyState: PokemonBattleState,
    var weather: Weather,
    var isSpecialScreenActive: Boolean = false,
    var isPhysicalScreenActive: Boolean = false,
)
