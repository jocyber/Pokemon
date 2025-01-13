package pokemongame.pokemon

enum class PokemonType {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY,
    UNKNOWN,
}

sealed interface TypeEffectiveness {
    val multiplier: Float

    operator fun times(other: TypeEffectiveness): TypeEffectiveness
}

data class SuperEffective private constructor(override val multiplier: Float) : TypeEffectiveness {
    companion object {
        operator fun invoke() = SuperEffective(multiplier = 2f)
    }

    override operator fun times(other: TypeEffectiveness) =
        when (other) {
            is SuperEffective -> SuperEffective(4f)
            is NormallyEffective -> SuperEffective()
            is NotVeryEffective -> NormallyEffective()
            is NotEffective -> NotEffective()
        }
}

data class NormallyEffective private constructor(override val multiplier: Float) :
    TypeEffectiveness {
    companion object {
        operator fun invoke() = NormallyEffective(multiplier = 1f)
    }

    override operator fun times(other: TypeEffectiveness) =
        when (other) {
            is SuperEffective -> SuperEffective()
            is NormallyEffective -> NormallyEffective()
            is NotVeryEffective -> NotVeryEffective()
            is NotEffective -> NotEffective()
        }
}

data class NotVeryEffective private constructor(override val multiplier: Float) :
    TypeEffectiveness {
    companion object {
        operator fun invoke() = NotVeryEffective(multiplier = 0.5f)
    }

    override operator fun times(other: TypeEffectiveness) =
        when (other) {
            is SuperEffective -> NormallyEffective()
            is NormallyEffective -> NotVeryEffective()
            is NotVeryEffective -> NotVeryEffective(0.25f)
            is NotEffective -> NotEffective()
        }
}

data class NotEffective private constructor(override val multiplier: Float) : TypeEffectiveness {
    companion object {
        operator fun invoke() = NotEffective(multiplier = 0f)
    }

    override operator fun times(other: TypeEffectiveness) =
        when (other) {
            is SuperEffective -> NotEffective()
            is NormallyEffective -> NotEffective()
            is NotVeryEffective -> NotEffective()
            is NotEffective -> NotEffective()
        }
}
