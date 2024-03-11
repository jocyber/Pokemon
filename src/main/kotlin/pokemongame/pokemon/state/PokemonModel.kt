package pokemongame.pokemon.state

import pokemongame.pokemon.Pokemon

class PokemonModel(
    var level: Int,
    var currentHealth: Int,
    val gender: Gender,
    // moves
    // ivs
    // evs
    // actualStats
    val pokemon: Pokemon,
)
