package pokemongame.pokemon.state

import pokemongame.moves.PokemonMove
import pokemongame.pokemon.Pokemon

class PokemonStats(
    var level: Int,
    currentHealth: Int,
    // val gender: Gender,
    val moves: MutableList<PokemonMove?>,
    // ivs
    // evs
    // actualStats
    var totalHealth: Int,
    val pokemon: Pokemon,
) {
    var currentHealth = currentHealth
        set(updatedHealth) {
            field =
                when {
                    updatedHealth < 0 -> 0
                    updatedHealth > totalHealth -> totalHealth
                    else -> updatedHealth
                }
        }
}
