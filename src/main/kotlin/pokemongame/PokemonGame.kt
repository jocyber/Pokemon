package pokemongame

import pokemongame.pokemon.*
import pokemongame.pokemon.state.MoveList
import pokemongame.pokemon.state.PokemonInstance
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.display.BattleDisplay

val battleDisplay =
    BattleDisplay(
        playerPokemon =
            PokemonInstance(
                Primeape,
                level = 10,
                moves = MoveList(SuckerPunch, Tackle),
            ),
        enemyPokemon =
            PokemonInstance(
                Zigzagoon,
                level = 10,
                moves = MoveList(Tackle),
            ),
        sceneState = BattleSceneState()
    )

fun main() {
    battleDisplay.display()
}
