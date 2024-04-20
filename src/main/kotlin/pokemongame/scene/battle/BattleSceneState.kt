package pokemongame.scene.battle

import com.lehaine.littlekt.math.Vec2f
import pokemongame.moves.Tackle
import pokemongame.pokemon.Primeape
import pokemongame.pokemon.Zigzagoon
import pokemongame.pokemon.state.PokemonStats
import pokemongame.scene.SceneState
import pokemongame.scene.Weather
import pokemongame.scene.battle.ui.HealthBar

data class BattleSceneState(
    var playerState: PokemonBattleState,
    var enemyState: PokemonBattleState,
    var turn: BattleEntity = BattleEntity.PLAYER,
    var weather: Weather,
    var battleState: BattleExecutionState = BattleExecutionState.USER_SELECTING_ACTION,
    var displayState: DisplayState = DisplayState.BATTLE,
    var isSpecialScreenActive: Boolean = false,
    var isPhysicalScreenActive: Boolean = false,
) : SceneState() {
    override val availableActions = listOf(BattleAction.FIGHT, BattleAction.BAG, BattleAction.POKEMON, BattleAction.RUN)

    lateinit var currentTarget: PokemonBattleState
    lateinit var opposingTarget: PokemonBattleState

    var selectedMove = "Tackle"

    init {
        selectedAction = availableActions[0]
    }
}

enum class DisplayState {
    BATTLE,
    PARTY,
    BAG,
}

enum class BattleExecutionState {
    USER_SELECTING_ACTION,
    USER_SELECTING_MOVE,
    EXECUTING_BATTLE,
}

fun buildBattleSceneState() =
    BattleSceneState(
        playerState =
        PokemonBattleState(
            stats =
            PokemonStats(
                level = 10,
                currentHealth = 37,
                pokemon = Primeape,
                totalHealth = 50,
                moves = mutableListOf(Tackle, null, null, null),
            ),
            position = Vec2f(125f, -200f),
            healthBar = HealthBar(37, 50, BattleEntity.PLAYER)
        ),
        enemyState =
        PokemonBattleState(
            stats =
            PokemonStats(
                level = 10,
                currentHealth = 5,
                pokemon = Zigzagoon,
                totalHealth = 10,
                moves = mutableListOf(Tackle, null, null, null),
            ),
            position = Vec2f(650f, 30f),
            healthBar = HealthBar(5, 10, BattleEntity.ENEMY),
        ),
        weather = Weather.SUN,
    )
    .apply {
        currentTarget = playerState
        opposingTarget = enemyState
    }
