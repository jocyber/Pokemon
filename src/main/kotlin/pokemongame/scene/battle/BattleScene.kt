package pokemongame.scene.battle

import com.google.inject.Injector
import com.google.inject.Key
import com.google.inject.name.Names
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import pokemongame.guice.HEALTH_BAR_TEXTURE
import pokemongame.pokemon.state.PokemonModel
import pokemongame.scene.Weather
import pokemongame.scene.battle.ui.HealthBar

class BattleScene(
    val context: Context,
    val backgroundTexture: Texture,
    val enemyPokemon: PokemonModel,
    val playerPokemon: PokemonModel,
    weather: Weather,
    val injector: Injector,
) {
    var enemyHealthBar: HealthBar? = null
    var playerHealthBar: HealthBar? = null

    private val healthBarTexture =
        injector.getInstance(Key.get(Texture::class.java, Names.named(HEALTH_BAR_TEXTURE)))

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
                texture = healthBarTexture,
            )
    }

    fun setPlayerPokemon(pokemonModel: PokemonModel) {
        playerHealthBar =
            HealthBar(
                currentHealth = 20,
                totalHealth = pokemonModel.currentHealth,
                isPlayer = true,
                texture = healthBarTexture,
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
