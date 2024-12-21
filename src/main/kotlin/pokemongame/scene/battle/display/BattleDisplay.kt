package pokemongame.scene.battle.display

import pokemongame.pokemon.state.PokemonInstance
import pokemongame.scene.battle.BattleSceneState

class BattleDisplay(
    private val playerPokemon: PokemonInstance,
    private val enemyPokemon: PokemonInstance,
    private val sceneState: BattleSceneState,
) {
    private var selectedMove = playerPokemon.moves[0]

    init {
        println("Starting the battle between $playerPokemon and $enemyPokemon!")
    }

    fun display() {
        fun PokemonInstance.healthBar() = "$this: $currentHealth/$totalHealth"

        println()
        println("--------------------------------")
        println("${playerPokemon.healthBar()}    ${enemyPokemon.healthBar()}\n")
        println("$playerPokemon: ")

        playerPokemon.moves.forEach {
            if (it == selectedMove) {
                println("$it <-")
            } else {
                println(it.toString())
            }
        }
    }
}
