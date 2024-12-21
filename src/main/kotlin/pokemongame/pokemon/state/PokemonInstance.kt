package pokemongame.pokemon.state

import pokemongame.pokemon.Pokemon
import pokemongame.pokemon.PokemonMove

data class PokemonInstance(
    private val pokemon: Pokemon,
    val level: Int,
    val moves: MoveList,
) : Pokemon by pokemon {
    val totalHealth = 100

    var currentHealth = totalHealth
        set(updatedHealth) {
            field = updatedHealth.coerceIn(0, totalHealth)
        }

    override fun toString() = pokemon.toString()
}

@JvmInline
value class MoveList private constructor(val value: List<PokemonMove>) :
    List<PokemonMove> by value {
    companion object {
        operator fun invoke(moves: List<PokemonMove>): MoveList? =
            when (moves.size) {
                in 0..4 -> MoveList(moves)
                else -> null
            }

        operator fun invoke() = MoveList(emptyList())

        operator fun invoke(move: PokemonMove) = MoveList(listOf(move))

        operator fun invoke(move1: PokemonMove, move2: PokemonMove) = MoveList(listOf(move1, move2))

        operator fun invoke(move1: PokemonMove, move2: PokemonMove, move3: PokemonMove) =
            MoveList(listOf(move1, move2, move3))

        operator fun invoke(
            move1: PokemonMove,
            move2: PokemonMove,
            move3: PokemonMove,
            move4: PokemonMove,
        ) = MoveList(listOf(move1, move2, move3, move4))
    }

    operator fun plus(newMove: PokemonMove) = invoke(value + newMove)
}
