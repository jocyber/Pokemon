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
    UNKNOWN;

    companion object {
        const val HALF_EFFECTIVE = 0.5f
        const val NORMALLY_EFFECTIVE = 1f
        const val SUPER_EFFECTIVE = 2f
    }
}
