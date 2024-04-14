package pokemongame.scene

import com.lehaine.littlekt.math.Vec2f
import pokemongame.pokemon.Primeape
import pokemongame.pokemon.Zigzagoon
import pokemongame.pokemon.state.PokemonStats
import pokemongame.scene.battle.BattleEntity
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.PokemonBattleState
import pokemongame.scene.battle.ui.HealthBar

private val enemyPosition = Vec2f(650f, 30f)
private val playerPosition = Vec2f(125f, -200f)

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
                        ),
                    position = playerPosition,
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
                        ),
                    position = enemyPosition,
                    healthBar = HealthBar(5, 10, BattleEntity.ENEMY),
                ),
            weather = Weather.SUN,
        )
        .apply {
            currentTarget = playerState
            opposingTarget = enemyState
        }
