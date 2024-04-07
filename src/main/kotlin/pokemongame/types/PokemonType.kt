package pokemongame.types

import pokemongame.pokemon.Pokemon

enum class PokemonType {
    FIRE,
    WATER,
    NORMAL,
    FIGHTING;

    companion object {
        private const val HALF_EFFECTIVE = 0.5f
        private const val NORMAL_EFFECTIVE = 1f

        fun typeEffectiveness(pokemon: Pokemon, attacking: PokemonType): Float {
            return typeEffectiveness(pokemon.type.first, attacking) *
                (pokemon.type.second?.let { typeEffectiveness(it, attacking) } ?: 1f)
        }

        private fun typeEffectiveness(defending: PokemonType, attacking: PokemonType): Float {
            return when (defending) {
                FIRE ->
                    when (attacking) {
                        FIRE,
                        WATER -> HALF_EFFECTIVE
                        else -> NORMAL_EFFECTIVE
                    }
                WATER ->
                    when (attacking) {
                        FIRE,
                        WATER -> HALF_EFFECTIVE
                        else -> NORMAL_EFFECTIVE
                    }
                else -> 1f
            }
        }
    }
}
