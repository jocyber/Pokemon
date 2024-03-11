package pokemongame.scene.battle

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
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
    var enemyHealthBar: HealthBar? = null
    var playerHealthBar: HealthBar? = null

    init {
        setEnemyPokemon(enemyPokemon)
        setPlayerPokemon(playerPokemon)
    }

    fun setEnemyPokemon(pokemonModel: PokemonModel) {
        enemyHealthBar =
            HealthBar(
                currentHealth = pokemonModel.currentHealth,
                totalHealth = pokemonModel.currentHealth,
                isPlayer = false,
            )
    }

    fun setPlayerPokemon(pokemonModel: PokemonModel) {
        playerHealthBar =
            HealthBar(
                currentHealth = pokemonModel.currentHealth,
                totalHealth = pokemonModel.currentHealth,
                isPlayer = true,
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
