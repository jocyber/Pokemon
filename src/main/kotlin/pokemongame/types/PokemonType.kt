package pokemongame.types

import pokemongame.pokemon.Pokemon

enum class PokemonType {
    FIRE,
    WATER,
    ELECTRIC,
    NORMAL;

    companion object {
        fun typeEffectiveness(pokemon: Pokemon, attacking: PokemonType): Float {
            return typeEffectiveness(pokemon.type.first, attacking) *
                (pokemon.type.second?.let { typeEffectiveness(it, attacking) } ?: 1f)
        }

        private fun typeEffectiveness(defending: PokemonType, attacking: PokemonType): Float {
            return when (defending) {
                FIRE ->
                    when (attacking) {
                        FIRE,
                        WATER -> 0.5f
                        else -> 1f
                    }
                WATER ->
                    when (attacking) {
                        FIRE,
                        WATER -> 0.5f
                        else -> 1f
                    }
                else -> 1f
            }
        }
    }
}
